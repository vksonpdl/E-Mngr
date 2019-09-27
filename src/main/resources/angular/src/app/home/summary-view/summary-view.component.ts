import { Component, OnInit } from '@angular/core';
import { ExpsummaryService } from './../../service/expsummary.service'

@Component({
  selector: 'app-summary-view',
  templateUrl: './summary-view.component.html',
  styleUrls: ['./summary-view.component.css']
})
export class SummaryViewComponent implements OnInit {

  appLoaded="NO";
  exp:any={}
  constructor(private expsummaryService:ExpsummaryService) { }

  ngOnInit() {

    this.expsummaryService.getSummary().toPromise().then(
      data=>{
       
       this.exp = data;
       this.appLoaded ="YES"
      }
    )
  }

}
