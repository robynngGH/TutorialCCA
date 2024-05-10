import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/category/model/Category';
import { Game } from '../model/Game';
import { GameService } from '../game.service';
import { CategoryService } from 'src/app/category/category.service';
import { MatDialog } from '@angular/material/dialog';
import { GameEditComponent } from '../game-edit/game-edit.component';
import { map, zip } from 'rxjs';

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.scss']
})
export class GameListComponent implements OnInit {

  categories: Category[];
  games: Game[];
  filterCategory: Category;
  filterTitle: string;

  constructor(
    private gameService: GameService,
    private categoryService: CategoryService,
    public dialog: MatDialog) {}

  ngOnInit(): void {
    //using zip to optimize resource management
    zip(
      this.gameService.getGames(),
      this.categoryService.getCategories()
    ).pipe(
      map(([games, categories]) => ({ games, categories }))
    ).subscribe(
      ({ games, categories }) => {
          this.games = games;
          this.categories = categories;
      }
    );
    //this version uses two subscriptions, consuming more resources
    /**
    this.gameService.getGames().subscribe(
      games => this.games = games
    );
    this.categoryService.getCategories().subscribe(
      categories => this.categories = categories
    ); */
  }

  onCleanFilter(): void {
    this.filterTitle = null;
    this.filterCategory = null;
    this.onSearch();
  }

  onSearch(): void {
    let title = this.filterTitle;
    let categoryId = this.filterCategory != null ? this.filterCategory.id : null;

    this.gameService.getGames(title, categoryId).subscribe(
      games => this.games = games
    );
  }

  createGame() {
    const dialogRef = this.dialog.open(GameEditComponent, {data: {}});

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

  editGame(game: Game) {
    const dialogRef = this.dialog.open(GameEditComponent, {
      data: {game: game}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.onSearch();
    });
  }
}
