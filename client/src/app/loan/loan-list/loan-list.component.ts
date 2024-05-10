import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { CustomerService } from 'src/app/customer/customer.service';
import { Customer } from 'src/app/customer/model/Customer';
import { GameService } from 'src/app/game/game.service';
import { Game } from 'src/app/game/model/Game';
import { Loan } from '../model/Loan';
import { LoanService } from '../loan.service';
import { PageEvent } from '@angular/material/paginator';
import { Pageable } from 'src/app/core/model/page/Pageable';
import { LoanEditComponent } from '../loan-edit/loan-edit.component';
import { DialogConfirmationComponent } from 'src/app/core/dialog-confirmation/dialog-confirmation.component';
import { map, zip } from 'rxjs';

@Component({
  selector: 'app-loan-list',
  templateUrl: './loan-list.component.html',
  styleUrls: ['./loan-list.component.scss']
})
export class LoanListComponent implements OnInit {

  games: Game[];
  customers: Customer[];
  //start_dates: Date[];
  //end_dates: Date[];
  filterGame: Game;
  filterCustomer: Customer;
  filterDate: Date;

  pageNumber: number = 0;
  pageSize: number = 5;
  totalElements: number = 0;

  dataSource = new MatTableDataSource<Loan>();
  displayedColumns: string[] = ['id', 'game', 'customer', 'start_date', 'end_date', 'action'];

  constructor(
    private gameService: GameService,
    private customerService: CustomerService,
    private loanService: LoanService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    //using zip to optimize resource management
    zip(
      this.gameService.getGames(),
      this.customerService.getCustomers()
    ).pipe(
      map(([games, customers]) => ({ games, customers }))
    ).subscribe(
      ({ games, customers }) => {
          this.games = games;
          this.customers = customers;
      }
    );
    
    this.loadPage();
  }

  loadPage(event?: PageEvent) {
    let pageable: Pageable = {
      pageNumber: this.pageNumber,
      pageSize: this.pageSize,
      sort: [{
        property: 'id',
        direction: 'ASC'
      }]
    }

    if(event != null) {
      pageable.pageSize = event.pageSize;
      pageable.pageNumber = event.pageIndex;
    }

    this.loanService.getLoans(pageable).subscribe(data => {
      this.dataSource.data = data.content;
      this.pageNumber = data.pageable.pageNumber;
      this.pageSize = data.pageable.pageSize;
      this.totalElements = data.totalElements;
    });
  }

  createLoan() {
    const dialogRef = this.dialog.open(LoanEditComponent, {
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

  deleteLoan(loan: Loan) {
    const dialogRef = this.dialog.open(DialogConfirmationComponent, {
      data: { title: "Eliminar préstamo", description: "Se perderán los datos del préstamo.<br>¿Seguro que desea eliminarlo?"}
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result) {
        this.loanService.deleteLoan(loan.id).subscribe(result => {
          this.ngOnInit();
        });
      }
    });
  }

  /**
   * Filters
   */

  onCleanFilter(): void {
    this.filterGame = null;
    this.filterCustomer = null;
    this.filterDate = null;
    this.onSearch();
  }

  onSearch(): void {
    let gameId = this.filterGame != null ? this.filterGame.id : null;
    let categoryId = this.filterCustomer != null ? this.filterCustomer.id : null;
    let date = this.filterDate != null ? this.filterDate.getDate : null;
  }
}
