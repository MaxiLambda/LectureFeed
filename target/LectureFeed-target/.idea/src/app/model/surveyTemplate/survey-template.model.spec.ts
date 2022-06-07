import {SurveyTemplate, SurveyType} from './survey-template.model';

describe('SurveyTemplate', () => {
  it('should create an instance', () => {
    expect(new SurveyTemplate(1, "name", SurveyType.YesNo, "question", 5, true)).toBeTruthy();
  });
});
