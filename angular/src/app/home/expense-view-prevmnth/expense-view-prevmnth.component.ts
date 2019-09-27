import { Component, OnInit } from '@angular/core';
import { ExpenseService } from './../../service/expense.service'

@Component({
  selector: 'app-expense-view-prevmnth',
  templateUrl: './expense-view-prevmnth.component.html',
  styleUrls: ['./expense-view-prevmnth.component.css']
})
export class ExpenseViewPrevmnthComponent implements OnInit {

  appLoaded ="NO";
  exp:any={}
  constructor(private expenseService: ExpenseService) { }

  ngOnInit() {
    this.expenseService.getPreviousMonthExpense().toPromise().then(
      data=>{
       
       this.exp = data;
       this.appLoaded ="YES"
      }
    )
  }

}
