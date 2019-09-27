import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AmntblncrAddComponent } from './amntblncr-add.component';

describe('AmntblncrAddComponent', () => {
  let component: AmntblncrAddComponent;
  let fixture: ComponentFixture<AmntblncrAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AmntblncrAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AmntblncrAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
