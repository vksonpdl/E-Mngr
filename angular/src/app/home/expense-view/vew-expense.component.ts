import { Component, OnInit } from '@angular/core';
import { ExpenseService } from './../../service/expense.service'

@Component({
  selector: 'app-vew-expense',
  templateUrl: './vew-expense.component.html',
  styleUrls: ['./vew-expense.component.css']
})
export class VewExpenseComponent implements OnInit {

  appLoaded ="NO";
  exp:any={}
  constructor(private expenseService: ExpenseService) { }

  ngOnInit() {
    this.expenseService.getThisMonthExpense().toPromise().then(
      data=>{
       
       this.exp = data;
       this.appLoaded ="YES"
      }
    )
  }

}
