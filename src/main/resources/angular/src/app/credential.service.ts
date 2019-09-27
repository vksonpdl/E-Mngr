import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http'
import {environment} from './../environments/environment'

@Injectable({
  providedIn: 'root'
})
export class CredentialService {

  loginReq={
    "un":"asdczxcz",
    "pwd":"asd"
  };

  passwordChangeReq={
    "pwd":"",
    "new_pwd":"",
    "new_pwd2":""
  }
 
  httpHeaders = new HttpHeaders()
     .set('Content-Type', 'application/json')
     .set('Cache-Control', 'no-cache'); 
 
     options = {
      headers: this.httpHeaders
 }; 

  constructor(private http:HttpClient) { }

  getSession(){
       return this.http.get(environment.url+"getsession");
  }
  login(data){
    this.loginReq.un = data.un;
    this.loginReq.pwd = data.pwd;
    

    return this.http.post(environment.url+"login",
    this.loginReq
         ,this.options);
  }
  changePassword(data){

    this.passwordChangeReq.pwd = data.pwd;
    this.passwordChangeReq.new_pwd = data.new_pwd;
    this.passwordChangeReq.new_pwd2 = data.new_pwd2;


    return this.http.post(environment.url+"changepassword",
    this.passwordChangeReq
         ,this.options);

  }
  logout(){
    return this.http.get(environment.url+"logout");
  }
  
}
