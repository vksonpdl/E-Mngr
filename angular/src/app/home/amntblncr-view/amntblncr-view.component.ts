import { Component, OnInit } from '@angular/core';
import { AmntblncrService } from './../../service/amntblncr.service'

@Component({
  selector: 'app-amntblncr-view',
  templateUrl: './amntblncr-view.component.html',
  styleUrls: ['./amntblncr-view.component.css']
})
export class AmntblncrViewComponent implements OnInit {

  appLoaded ="NO";
  exp:any={};

  constructor( private amntblncrService: AmntblncrService) { }

  ngOnInit() {
    this.amntblncrService.getThisMonthBalancerFn().toPromise().then(
      data=>{
       this.exp = data;
       this.appLoaded="YES"
      }
    )
  }

}
