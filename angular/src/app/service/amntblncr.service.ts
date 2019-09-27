import { Injectable } from '@angular/core';


import { HttpClient,HttpHeaders } from '@angular/common/http'
import {environment} from './../../environments/environment'

@Injectable({
  providedIn: 'root'
})
export class AmntblncrService {

  httpHeaders = new HttpHeaders()
  .set('Content-Type', 'application/json')
  .set('Cache-Control', 'no-cache'); 

  options = {
   headers: this.httpHeaders
}; 

  constructor(private http:HttpClient) { }

  getThisMonthBalancerFn(){
    return this.http.get(environment.url+"getmonthbalancer"); 
  }

  getPreviousMonthBalancerFn(){
    return this.http.get(environment.url+"getmonthbalancer?prevmnth=true"); 
  }
  getUsers(){
    return this.http.get(environment.url+"getusers"); 
  }
  addAmount(data){
    return this.http.post(environment.url+"addamount",
    data,this.options);
  }
}
