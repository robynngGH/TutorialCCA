import { Component, Inject, OnInit } from '@angular/core';
import { Category } from '../model/Category';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CategoryService } from '../category.service';

@Component({
  selector: 'app-category-edit',
  templateUrl: './category-edit.component.html',
  styleUrls: ['./category-edit.component.scss']
})
export class CategoryEditComponent implements OnInit {

  category: Category;

  constructor(
    public dialogRef: MatDialogRef<CategoryEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private categoryService: CategoryService
  ) { }

  /**
   * Injects data to the category model if editing,
   * otherwise it creates a new model
   */
  ngOnInit(): void {
    if (this.data.category != null) {
      this.category = Object.assign({}, this.data.category);
    }
    else {
      this.category = new Category();
    }
  }

  onSave() {
    this.categoryService.saveCategory(this.category).subscribe(result => {
      this.dialogRef.close();
    });    
  }  

  onClose() {
    this.dialogRef.close();
  }
}
