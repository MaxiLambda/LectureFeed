import {TestBed} from '@angular/core/testing';

import {SessionService} from './session.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('SessionService', () => {

  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
    providers: [SessionService]
  }));

  it('should create an instance', () => {
    const service: SessionService = TestBed.get(SessionService);
    expect(service).toBeTruthy();
  });

  it('should have getData function', () => {
    const service: SessionService = TestBed.get(SessionService);
    expect(service.getInitialData).toBeTruthy();
  });

});
