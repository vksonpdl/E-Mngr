import { Component, OnInit } from '@angular/core';
import { ExpsummaryService } from './../../service/expsummary.service'

@Component({
  selector: 'app-summary-view-prevmnth',
  templateUrl: './summary-view-prevmnth.component.html',
  styleUrls: ['./summary-view-prevmnth.component.css']
})
export class SummaryViewPrevmnthComponent implements OnInit {

  appLoaded="NO";
  exp:any={}
  constructor(private expsummaryService:ExpsummaryService) { }

  ngOnInit() {
    this.expsummaryService.getPrevMnthSummary().toPromise().then(
      data=>{
       
       this.exp = data;
       this.appLoaded ="YES"
      }
    )
  }

}
