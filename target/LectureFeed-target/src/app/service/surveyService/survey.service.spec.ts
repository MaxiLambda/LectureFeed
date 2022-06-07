import {TestBed} from '@angular/core/testing';

import {SurveyService} from './survey.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('SurveyService', () => {

  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
    providers: [SurveyService]
  }));

  it('should create an instance', () => {
    const service: SurveyService = TestBed.get(SurveyService);
    expect(service).toBeTruthy();
  });

});
