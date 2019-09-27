import { Component, OnInit } from '@angular/core';
import { AmntblncrService } from './../../service/amntblncr.service'

@Component({
  selector: 'app-amntblncr-view-prevmnth',
  templateUrl: './amntblncr-view-prevmnth.component.html',
  styleUrls: ['./amntblncr-view-prevmnth.component.css']
})
export class AmntblncrViewPrevmnthComponent implements OnInit {

  appLoaded ="NO";
  exp:any={};

  constructor( private amntblncrService: AmntblncrService) { }

  ngOnInit() {
    this.amntblncrService.getPreviousMonthBalancerFn().toPromise().then(
      data=>{
       this.exp = data;
       this.appLoaded="YES"
      }
    )
  }

}
