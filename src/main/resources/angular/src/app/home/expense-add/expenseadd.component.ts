import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../../service/category.service'
import { ExpenseService } from '../../service/expense.service'


@Component({
  selector: 'expense-add-home',
  templateUrl: './expenseadd.component.html', 
  styleUrls: ['./expenseadd.component.css']
})
export class ExpenseAddComponent implements OnInit{
  
  title = 'emngr';
  cat: any=[];
  expnse_add_response : any={};
  data :any ={
  };
  appLoaded ="NO"

  constructor( private catService: CategoryService, private expenseService: ExpenseService){
    
  }
  ngOnInit() {
    this.catService.getCategories().toPromise().then(
      data=>{
       
       this.cat = data;
       this.appLoaded ="YES"
      }
    )
  }

  onSubmit(datapasses){
    this.expenseService.addExpense(datapasses).toPromise().then(
      datas=>{
        this.expnse_add_response=datas;
        this.data.exp=0
      }   
    );
    }
  
}
