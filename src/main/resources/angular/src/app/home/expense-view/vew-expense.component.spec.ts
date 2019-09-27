import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VewExpenseComponent } from './vew-expense.component';

describe('VewExpenseComponent', () => {
  let component: VewExpenseComponent;
  let fixture: ComponentFixture<VewExpenseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VewExpenseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VewExpenseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
