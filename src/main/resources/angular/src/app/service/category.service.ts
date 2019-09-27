import { Injectable } from '@angular/core';

import { HttpClient,HttpHeaders } from '@angular/common/http'
import {environment} from './../../environments/environment'

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http:HttpClient) { }

  httpHeaders = new HttpHeaders()
  .set('Content-Type', 'application/json')
  .set('Cache-Control', 'no-cache'); 

  options = {
   headers: this.httpHeaders
}; 

getCategories(){
  return this.http.get(environment.url+"getcategories");


  
}

}
