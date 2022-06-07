export enum SurveyType{
  YesNo = 0,
  Rating = 1 ,
  OpenAnswer = 2
}

export interface ISurveyTemplate{
  name: string
  type: SurveyType
  question: string
  duration: number
  publishResults: boolean
}

export class SurveyTemplate {
  private readonly _id: number
  private readonly _name: string
  private readonly _type: SurveyType
  private readonly _question: string
  private readonly _duration: number
  private readonly _publishResults: boolean

  constructor(id: number, name: string, type: SurveyType, question: string, duration: number, publishResults: boolean) {
    this._id = id;
    this._name = name;
    this._type = SurveyType[type] as any;
    this._question = question;
    this._duration = duration;
    this._publishResults = publishResults;
  }

  get id(): number {
    return this._id;
  }

  get name(): string {
    return this._name;
  }

  get type(): SurveyType {
    return this._type;
  }

  get question(): string {
    return this._question;
  }

  get duration(): number {
    return this._duration;
  }

  get publishResults(): boolean {
    return this._publishResults;
  }

  toInterface(): ISurveyTemplate
  {
    return {
      name: this._name,
      type: this._type,
      question: this._question,
      duration: this._duration,
      publishResults: this._publishResults,
    }
  }
}
