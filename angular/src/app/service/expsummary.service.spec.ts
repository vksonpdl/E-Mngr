import { TestBed } from '@angular/core/testing';

import { ExpsummaryService } from './expsummary.service';

describe('ExpsummaryService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ExpsummaryService = TestBed.get(ExpsummaryService);
    expect(service).toBeTruthy();
  });
});
