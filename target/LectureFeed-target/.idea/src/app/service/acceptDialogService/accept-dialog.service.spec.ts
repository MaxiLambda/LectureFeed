import { TestBed } from '@angular/core/testing';

import { AcceptDialogService } from './accept-dialog.service';

describe('AcceptDialogService', () => {
  let service: AcceptDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AcceptDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
