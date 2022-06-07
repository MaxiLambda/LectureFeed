import {Participant} from "../participant/participant.model";
import {Question} from "../question/question.model";
import {Survey} from "../survey/survey.model";
import {MoodEntity} from "../moodEntitiy/mood-entity.model";

export class Session {
  private readonly _id: number
  private readonly _name: string
  private readonly _sessionCode: string
  private readonly _participants: Participant[]
  private readonly _moodEntities: MoodEntity[]
  private readonly _questions: {[key: string]: Question};
  private readonly _surveys: {[key: string]: Survey};
  private readonly _closed: number;

  constructor(id: number, name: string, sessionCode: string, participants: Participant[], moodEntities: MoodEntity[], questions: {[key: string]: Question}, surveys: {[key: string]: Survey}, closed: number) {
   this._id = id;
   this._name = name;
   this._sessionCode = sessionCode;
   this._participants = participants;
   this._moodEntities = moodEntities;
   this._questions = questions;
   this._surveys = surveys;
   this._closed = closed;
  }


  get id(): number {
    return this._id;
  }

  get name(): string {
    return this._name;
  }

  get sessionCode(): string {
    return this._sessionCode;
  }

  get participants(): Participant[] {
    return this._participants;
  }

  get moodEntities(): MoodEntity[] {
    return this._moodEntities;
  }

  get questions(): { [p: string]: Question } {
    return this._questions;
  }

  get surveys(): { [p: string]: Survey } {
    return this._surveys;
  }

  get closed(): number {
    return this._closed;
  }

}
