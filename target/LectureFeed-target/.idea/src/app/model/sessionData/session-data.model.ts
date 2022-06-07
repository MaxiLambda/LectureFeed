import {Question} from "../question/question.model";
import {Participant} from "../participant/participant.model";

export class SessionData {
  private readonly _questions: Question[];
  private readonly _participants: Participant[];

  constructor(questions: Question[], participants: Participant[]) {
    this._questions = questions;
    this._participants = participants;
  }

  get questions(): Question[] {
    return this._questions;
  }

  get participants(): Participant[] {
    return this._participants;
  }



}
