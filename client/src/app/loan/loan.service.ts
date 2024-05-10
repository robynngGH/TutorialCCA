import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Loan } from './model/Loan';
import { LOAN_DATA } from './model/mock-loans';
import { Pageable } from '../core/model/page/Pageable';
import { LoanPage } from './model/LoanPage';
import { HttpClient } from '@angular/common/http';
import { LOAN_DATA_LIST } from './model/mock-loans-list';

@Injectable({
  providedIn: 'root'
})
export class LoanService {

  constructor(private http: HttpClient) { }

  getLoans(pageable: Pageable): Observable<LoanPage> {
    return this.http.post<LoanPage>('http://localhost:8080/loan', {pageable:pageable});
  }

  saveLoan(loan: Loan): Observable<void> {
    let url = 'http://localhost:8080/loan';

    return this.http.put<void>(url, loan); //there's no editing option in this section
  }

  deleteLoan(idLoan: number): Observable<void> {
    return this.http.delete<void>('http://localhost:8080/loan/' + idLoan);
  }

}
