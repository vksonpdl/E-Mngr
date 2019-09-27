import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http'
import {environment} from './../../environments/environment'

@Injectable({
  providedIn: 'root'
})
export class ExpsummaryService {

  httpHeaders = new HttpHeaders()
  .set('Content-Type', 'application/json')
  .set('Cache-Control', 'no-cache'); 
  
  options = {
    headers: this.httpHeaders
 }; 
  constructor(private http:HttpClient) { }

  getSummary(){
    return this.http.get(environment.url+"getmonthsummary");
  
  }

  getPrevMnthSummary(){
    return this.http.get(environment.url+"getmonthsummary?prevmnth=true");
  
  }
}
