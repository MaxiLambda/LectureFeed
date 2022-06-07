import { TestBed } from '@angular/core/testing';

import { CustomRouterService } from './custom-router.service';
import {RouterTestingModule} from "@angular/router/testing";

describe('CustomRouterService', () => {
  let service: CustomRouterService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule]
    });
    service = TestBed.inject(CustomRouterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
