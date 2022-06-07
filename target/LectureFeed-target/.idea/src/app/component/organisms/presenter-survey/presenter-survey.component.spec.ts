import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PresenterSurveyComponent} from './presenter-survey.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {SurveyService} from "../../../service/surveyService/survey.service";

describe('PresenterSurveyComponent', () => {
  let component: PresenterSurveyComponent;
  let fixture: ComponentFixture<PresenterSurveyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers:[SurveyService],
      declarations: [PresenterSurveyComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PresenterSurveyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create an instance', () => {
    expect(component).toBeTruthy();
  });
});
