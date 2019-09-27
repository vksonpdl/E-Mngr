import { Component } from '@angular/core';
import { AppComponent } from './../app.component'
import { Observable } from 'rxjs';



@Component({
  selector: 'login-root',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  sessionData={};
data:any={}


errorMessage="";
observableData: Observable<Object>
loginData={};
loginMsg="";
  
  onSubmit(datapasses){
    
    this.appComponent.login(datapasses).toPromise().then(
      datas=>{
        this.data = datas;
        // Get Session Info anf if Login Successfull Redirect to the Hope Page
    this.appComponent.ngOnInit();
    this.sessionData = this.appComponent.sessionData; 

      }
    );
  }

  clear(){
    this.data.un="";
    this.data.pwd="";
    this.data.rmessage=""
      
  }
  constructor(private appComponent: AppComponent){

  this.sessionData = appComponent.sessionData;
  this.loginMsg = appComponent.getLoginMsg();    
  }
  
}
