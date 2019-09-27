import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SummaryViewPrevmnthComponent } from './summary-view-prevmnth.component';

describe('SummaryViewPrevmnthComponent', () => {
  let component: SummaryViewPrevmnthComponent;
  let fixture: ComponentFixture<SummaryViewPrevmnthComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SummaryViewPrevmnthComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SummaryViewPrevmnthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
