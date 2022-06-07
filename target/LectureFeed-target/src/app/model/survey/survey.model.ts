import {SurveyTemplate} from "../surveyTemplate/survey-template.model";

export class Survey{
  private readonly _id: number
  private readonly _answers: string[]
  private readonly _timestamp: number
  private readonly _template: SurveyTemplate

  constructor(id: number, template: SurveyTemplate,  answers: string[], timestamp: number) {
    this._id = id;
    this._template = template;
    this._answers = answers;
    this._timestamp = timestamp;
  }

  get id(): number {
    return this._id;
  }

  get template(): SurveyTemplate {
    return this._template;
  }

  get answers(): string[] {
    return this._answers;
  }

  get timestamp(): number {
    return this._timestamp;
  }

}

