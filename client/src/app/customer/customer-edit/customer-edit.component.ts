import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { Customer } from '../model/Customer';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CategoryEditComponent } from 'src/app/category/category-edit/category-edit.component';
import { CustomerService } from '../customer.service';
import { MatFormField, MatFormFieldControl } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';

@Component({
  selector: 'app-customer-edit',
  templateUrl: './customer-edit.component.html',
  styleUrls: ['./customer-edit.component.scss']
})
export class CustomerEditComponent implements OnInit{
  customer: Customer;
  unique: boolean = true; //checks that the name is unique
  //@ViewChild("nameField")
  //nameField!: MatFormFieldControl<MatInput>;

  constructor(
    public dialogRef: MatDialogRef<CategoryEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private customerService: CustomerService
  ) {}

  /**
   * Injects data to the customer model if editing,
   * otherwise it creates a new model
   */
  ngOnInit(): void {
    if (this.data.customer != null) {
      this.customer = Object.assign({}, this.data.customer);
    }
    else this.customer = new Customer();
  }

  onSave() {
    if(!this.customer.name) {
      this.unique = true; //avoids showing both errors
      return; //avoids going through the rest of the function
    }

    this.customerService.saveCustomer(this.customer).subscribe(result => {
      this.dialogRef.close();
    }, error => {
      //if (error.status == 500)
        this.unique = false; //if an error is found, the name already existed
    });
  }

  resetUnique() {
    if (!this.unique)
      this.unique = true; //resets unique to true when text changes
  }

  onClose() {
    if (!this.unique)
      this.unique = true;
    this.dialogRef.close();
  }
}
