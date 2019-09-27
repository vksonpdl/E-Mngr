import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http'
import {environment} from '../../environments/environment'

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {
  httpHeaders = new HttpHeaders()
  .set('Content-Type', 'application/json')
  .set('Cache-Control', 'no-cache'); 

  options = {
   headers: this.httpHeaders
}; 
  constructor(private http:HttpClient) { }

  addExpense(data){
    return this.http.post(environment.url+"addexpense",data,this.options);
  }

  getThisMonthExpense(){
    return this.http.get(environment.url+"getmonthexpense");
  }

  getPreviousMonthExpense(){
    return this.http.get(environment.url+"getmonthexpense?prevmnth=true");
  }

}
