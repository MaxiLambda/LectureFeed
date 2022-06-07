import {Survey} from './survey.model';
import {SurveyTemplate, SurveyType} from "../surveyTemplate/survey-template.model";

describe('Survey', () => {
  let survey = new Survey(99, new SurveyTemplate(1, "name", SurveyType.YesNo, "question", 5, true), [], new Date().getTime());

  it('should create an instance', () => {
    expect(survey).toBeTruthy();
  });
});
