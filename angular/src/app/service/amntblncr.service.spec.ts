import { TestBed } from '@angular/core/testing';

import { AmntblncrService } from './amntblncr.service';

describe('AmntblncrService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AmntblncrService = TestBed.get(AmntblncrService);
    expect(service).toBeTruthy();
  });
});
