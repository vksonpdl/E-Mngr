import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule ,routingComponent} from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component'
import { LoginComponent } from './login/login.component';
import { HomeMainComponent } from './home/home-main/home-main.component';
import { AmntblncrViewComponent } from './home/amntblncr-view/amntblncr-view.component'

import { CredentialService } from './credential.service'
import { CategoryService } from './service/category.service'
import { ExpenseService } from './service/expense.service';
import { AmntblncrService } from './service/amntblncr.service';
import { AmntblncrAddComponent } from './home/amntblncr-add/amntblncr-add.component';
import { SummaryViewComponent } from './home/summary-view/summary-view.component'
import { ExpsummaryService } from './service/expsummary.service';
import { SummaryViewPrevmnthComponent } from './home/summary-view-prevmnth/summary-view-prevmnth.component';
import { ExpenseViewPrevmnthComponent } from './home/expense-view-prevmnth/expense-view-prevmnth.component';
import { AmntblncrViewPrevmnthComponent } from './home/amntblncr-view-prevmnth/amntblncr-view-prevmnth.component';
import { PasswordChangeComponent } from './home/password-change/password-change.component'
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    routingComponent,
    HomeMainComponent,
    AmntblncrViewComponent,
    AmntblncrAddComponent,
    SummaryViewComponent,
    SummaryViewPrevmnthComponent,
    ExpenseViewPrevmnthComponent,
    AmntblncrViewPrevmnthComponent,
    PasswordChangeComponent,
    
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    CredentialService,
    CategoryService,
    AppComponent,
    ExpenseService,
    AmntblncrService,
    ExpsummaryService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
