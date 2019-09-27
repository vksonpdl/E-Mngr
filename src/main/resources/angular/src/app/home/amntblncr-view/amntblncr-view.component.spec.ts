import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AmntblncrViewComponent } from './amntblncr-view.component';

describe('AmntblncrViewComponent', () => {
  let component: AmntblncrViewComponent;
  let fixture: ComponentFixture<AmntblncrViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AmntblncrViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AmntblncrViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
