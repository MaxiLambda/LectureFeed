import {TestBed} from '@angular/core/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {AuthenticationService} from './authentication.service';

describe('AuthenticationService', () => {

  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
    providers: [AuthenticationService]
  }));

  it('should create an instance', () => {
    const service: AuthenticationService = TestBed.get(AuthenticationService);
    expect(service).toBeTruthy();
  });

  it('should have getData function', () => {
    const service: AuthenticationService = TestBed.get(AuthenticationService);
    expect(service.getAdminToken).toBeTruthy();
  });

});
