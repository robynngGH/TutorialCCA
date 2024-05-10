import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Category } from './model/Category';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) { }

  /**
   * Categories getter
   * @returns
   */
  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>('http://localhost:8080/category')
  }

  /**
   * Receives a new category name and updates it
   * @param category 
   * @returns 
   */
  saveCategory(category: Category): Observable<Category> {
    let url = 'http://localhost:8080/category';
    if (category.id != null)
      url += '/' + category.id; //puts new category in http://localhost:8080/category/new_id

    return this.http.put<Category>(url, category);
  }

  /**
   * Receives the ID of the category and removes it
   * @param idCategory 
   * @returns 
   */
  deleteCategory(idCategory : number): Observable<any> {
    return this.http.delete('http://localhost:8080/category/' + idCategory);
  }  
}
