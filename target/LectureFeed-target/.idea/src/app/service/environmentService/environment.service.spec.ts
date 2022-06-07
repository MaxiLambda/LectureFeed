import { TestBed } from '@angular/core/testing';

import { EnvironmentService } from './environment.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('EnvironmentService', () => {
  let service: EnvironmentService;

  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
    providers: [EnvironmentService]
  }));

  it('should create an instance', () => {
    const service: EnvironmentService = TestBed.get(EnvironmentService);
    expect(service).toBeTruthy();
  });

});
