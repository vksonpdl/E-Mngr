import { Component, OnInit } from '@angular/core';
import { AmntblncrService } from './../../service/amntblncr.service'

@Component({
  selector: 'app-amntblncr-add',
  templateUrl: './amntblncr-add.component.html',
  styleUrls: ['./amntblncr-add.component.css']
})
export class AmntblncrAddComponent implements OnInit {

  appLoaded ="NO";
  usrs:any={};
  resp:any={};
  data:any={}


  constructor(private amntblncrService: AmntblncrService) { }

  ngOnInit() {
    this.amntblncrService.getUsers().toPromise().then(
      resp=>{
       this.usrs = resp;
       this.appLoaded="YES"
      }
    )
  
  }

  onSubmit(data){
    this.amntblncrService.addAmount(data).toPromise().then(
      resp=>{
        this.resp = resp;
      }
    )
  }

}
