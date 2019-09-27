import { Component, OnInit } from '@angular/core';

import { AppComponent } from './../../app.component'
import { CredentialService } from './../../credential.service'
import { Observable } from 'rxjs';

@Component({
  selector: 'app-password-change',
  templateUrl: './password-change.component.html',
  styleUrls: ['./password-change.component.css']
})
export class PasswordChangeComponent implements OnInit {

  observableData: Observable<Object>
  sessionData={};
  response={};
  data:any={}
  constructor(private appComponent: AppComponent){

    this.sessionData = appComponent.sessionData;    
    
    
  }

  ngOnInit() {
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
