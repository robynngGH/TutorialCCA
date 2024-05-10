import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Customer } from './model/Customer';
import { CUSTOMER_DATA } from './model/mock-customers';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }

  /**
   * Gets all customers through an Observable
   * @returns
   */
  getCustomers(): Observable<Customer[]> {
    return this.http.get<Customer[]>('http://localhost:8080/customer');
  }

  /**
   * Receives a new customer name and updates it or saves it as a new one
   * @param customer
   * @returns 
   */
  saveCustomer(customer: Customer): Observable<Customer> {
    let url = 'http://localhost:8080/customer';
    if (customer.id != null)
      url += '/' + customer.id; //updates customer in <url>/<id> or adds it a new ID if it's a new customer

    return this.http.put<Customer>(url, customer);
  }

  /**
   * Receives the ID of a customer and removes it
   * @param idCustomer 
   * @returns 
   */
  deleteCustomer(idCustomer: number): Observable<any> {
    return this.http.delete('http://localhost:8080/customer/' + idCustomer);
  }
}
