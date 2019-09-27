import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ExpenseAddComponent } from './home/expense-add/expenseadd.component'
import { VewExpenseComponent } from './home/expense-view/vew-expense.component'
import { HomeMainComponent } from './home/home-main/home-main.component'
import { AmntblncrViewComponent } from './home/amntblncr-view/amntblncr-view.component'
import { AmntblncrAddComponent} from './home/amntblncr-add/amntblncr-add.component'
import { SummaryViewComponent } from './home/summary-view/summary-view.component'
import { SummaryViewPrevmnthComponent } from './home/summary-view-prevmnth/summary-view-prevmnth.component'
import { ExpenseViewPrevmnthComponent } from './home/expense-view-prevmnth/expense-view-prevmnth.component'
import { AmntblncrViewPrevmnthComponent} from './home/amntblncr-view-prevmnth/amntblncr-view-prevmnth.component'
import { PasswordChangeComponent } from './home/password-change/password-change.component'



const routes: Routes = [
  {path:"", component:SummaryViewComponent},
  {path:"add-expense", component:ExpenseAddComponent},
  {path:"view-expense", component:VewExpenseComponent},
  {path:"view-amntblncr",component:AmntblncrViewComponent},
  {path :"add-amntblncr", component: AmntblncrAddComponent},
  {path :"view-summary", component: SummaryViewComponent},
  {path :"view-summary-prevmnth", component: SummaryViewPrevmnthComponent},
  {path:"view-expense-prevmnth", component:ExpenseViewPrevmnthComponent},
  {path:"view-amntblncr-prevmnth",component:AmntblncrViewPrevmnthComponent},
  {path:"change-password",component:PasswordChangeComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponent = [
  ExpenseAddComponent,
  HomeMainComponent,
  VewExpenseComponent,
  AmntblncrViewComponent,
  AmntblncrAddComponent,
  SummaryViewComponent,
  SummaryViewPrevmnthComponent,
  ExpenseViewPrevmnthComponent,
  AmntblncrViewPrevmnthComponent,
  PasswordChangeComponent
]
