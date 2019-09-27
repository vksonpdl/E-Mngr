import { Component, OnInit } from '@angular/core';
import { CredentialService } from './credential.service'
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  observableData: Observable<Object>
  sessionData={};
  loginmsg ="START";
  
  loginData={};
  

  constructor( private credentialService: CredentialService){
    
  }
  ngOnInit(){

    this.observableData = this.credentialService.getSession();
    this.observableData.subscribe(
      sessionData => {
        this.sessionData = sessionData
       
      }
    
    )
   
  }
  login(data){
    return this.credentialService.login(data);
    
  }

  getLoginMsg(){
    return this.loginmsg;
  }

  setLoginMsg(msg){
    this.loginmsg=msg;
  }
  logout(){
   return this.observableData = this.credentialService.logout(); 
  }

  changePassword(data){
    return this.observableData = this.credentialService.changePassword(data); 
   }
  
 
}
