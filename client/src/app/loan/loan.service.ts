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

  getLoans(pageable: Pageable, gameId?: number, customerId?: number, date?: Date): Observable<LoanPage> {
    return this.http.post<LoanPage>(this.composeFindUrl(gameId, customerId, date), {pageable:pageable});
  }

  saveLoan(loan: Loan): Observable<void> {
    let url = 'http://localhost:8080/loan';

    return this.http.put<void>(url, loan); //there's no editing option in this section
  }

  deleteLoan(idLoan: number): Observable<void> {
    return this.http.delete<void>('http://localhost:8080/loan/' + idLoan);
  }

  //params in the URL to filter loans
  private composeFindUrl(gameId?: number, customerId?: number, date?: Date) : string {
    let params = '';

    if (gameId != null)
      params += 'idGame=' + gameId;

    if (customerId != null) {
      if (params != '')
        params += '&';
      params += 'idCustomer=' + customerId;
    }

    if (date != null) {
      if (params != '')
        params += '&';
      params += 'date=' + date.getFullYear() + '-' + ((date.getMonth() + 1) < 10 ? ('0' + (date.getMonth() + 1)) : (date.getMonth() + 1)) + '-' + (date.getDate() < 10 ? ('0' + date.getDate()) : date.getDate());
    }

    let url = 'http://localhost:8080/loan';

    if (params == '')
      return url;
    else
      return url + '?' + params;
  }

}
