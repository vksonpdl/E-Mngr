import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpenseViewPrevmnthComponent } from './expense-view-prevmnth.component';

describe('ExpenseViewPrevmnthComponent', () => {
  let component: ExpenseViewPrevmnthComponent;
  let fixture: ComponentFixture<ExpenseViewPrevmnthComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExpenseViewPrevmnthComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExpenseViewPrevmnthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
