import { TestBed } from '@angular/core/testing';

import { WaitDialogService } from './wait-dialog.service';

describe('WaitDialogService', () => {
  let service: WaitDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WaitDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
