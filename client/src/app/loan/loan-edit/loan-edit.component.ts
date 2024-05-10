import { Component, Inject, OnInit } from '@angular/core';
import { Loan } from '../model/Loan';
import { Customer } from 'src/app/customer/model/Customer';
import { Game } from 'src/app/game/model/Game';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { LoanService } from '../loan.service';
import { CustomerService } from 'src/app/customer/customer.service';
import { GameService } from 'src/app/game/game.service';

@Component({
  selector: 'app-loan-edit',
  templateUrl: './loan-edit.component.html',
  styleUrls: ['./loan-edit.component.scss']
})
export class LoanEditComponent implements OnInit{

  loan: Loan;
  customers: Customer[];
  games: Game[];

  constructor(
    public dialogRef: MatDialogRef<LoanEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private loanService: LoanService,
    private customerService: CustomerService,
    private gameService: GameService
  ) {}

  ngOnInit(): void {
      this.loan = new Loan();

      this.customerService.getCustomers().subscribe(
        customers => {
          this.customers = customers;
          if (this.loan.customer != null) {
            let customerFilter: Customer[] = customers.filter(customer => customer.id == this.data.loan.customer.id);
            if (customerFilter != null)
              this.loan.customer = customerFilter[0];
          }
        }
      );

      this.gameService.getGames().subscribe(
        games => {
          this.games = games;
          if (this.loan.game != null) {
            let gameFilter: Game[] = games.filter(game => game.id == this.data.loan.game.id);
            if (gameFilter != null)
              this.loan.game = gameFilter[0];
          }
        }
      );
  }

  onSave() {
    this.loanService.saveLoan(this.loan).subscribe(result => {
      this.dialogRef.close();
    });
  }

  onClose() { this.dialogRef.close() }
}
