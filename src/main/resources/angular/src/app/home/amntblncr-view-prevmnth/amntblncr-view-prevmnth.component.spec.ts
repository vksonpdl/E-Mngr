import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AmntblncrViewPrevmnthComponent } from './amntblncr-view-prevmnth.component';

describe('AmntblncrViewPrevmnthComponent', () => {
  let component: AmntblncrViewPrevmnthComponent;
  let fixture: ComponentFixture<AmntblncrViewPrevmnthComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AmntblncrViewPrevmnthComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AmntblncrViewPrevmnthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
