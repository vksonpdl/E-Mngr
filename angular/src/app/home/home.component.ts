import { Component, OnInit } from '@angular/core';
import { AppComponent } from './../app.component'




@Component({
  selector: 'home-root',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent{
  title = 'emngr';
  sessionData={};
  getsessionCalled=false;
  data:any={}
  loginMsg="";
  response={};
 
   
  constructor(private appComponent: AppComponent){

    this.sessionData = appComponent.sessionData;
    this.getsessionCalled=true;    
    this.loginMsg = appComponent.getLoginMsg();    
    }

    logout(){
      
      this.appComponent.logout().toPromise().then(
        data=>{
          // Get Session Info anf if Login Successfull Redirect to the Hope Page
          this.appComponent.ngOnInit();
          this.sessionData = this.appComponent.sessionData; 
        }
      );
      
  
    }
    onSubmit(datapasses){
    
      console.log("Method Called"+datapasses.pwd)
      
      this.appComponent.changePassword(datapasses).toPromise().then(
        datas=>{
          this.response = datas;
  
          
          // Get Session Info anf if Login Successfull Redirect to the Hope Page
          this.appComponent.ngOnInit();
          this.sessionData = this.appComponent.sessionData;
          this.appComponent.setLoginMsg("PWD_CHANGE")
          
  
        }
      );
    }

  clear(){
    
    this.data.pwd="";
    this.data.new_pwd=""
    this.data.new_pwd2=""
      
  }
  
}
