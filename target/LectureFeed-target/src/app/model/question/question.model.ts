import {Participant} from "../participant/participant.model";

export interface IQuestionTemplate{
  participantId: number
  anonymous: boolean
  message: string
  created: number
}




export class Question {
  private readonly _id: number;
  private readonly _participant: Participant;
  private readonly _message: string;
  private readonly _rating: number = 0;
  private readonly _created: number;
  private readonly _closed: number|null;

  constructor(id: number, participant: Participant, message: string, rating: number, created: number, closed: number | null = null) {
    this._id = id;
    this._participant = participant;
    this._message = message;
    this._rating = rating;
    this._created = created;
    this._closed = closed;
  }

  get id(): number {
    return this._id;
  }

  get participant(): Participant {
    return this._participant;
  }

  get message(): string {
    return this._message;
  }

  get rating(): number {
    return this._rating;
  }

  get created(): number {
    return this._created;
  }

  get closed(): number | null {
    return this._closed;
  }
}
