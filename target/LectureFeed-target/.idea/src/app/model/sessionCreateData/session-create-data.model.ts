export interface ISessionData{
  id: number,
  sessionCode: string
}

export class SessionCreateData implements ISessionData{
  private readonly _id: number
  private readonly _sessionCode: string

  constructor(id: number, sessionCode: string) {
    this._id = id;
    this._sessionCode = sessionCode;
  }

  get id(): number {
    return this._id;
  }

  get sessionCode(): string {
    return this._sessionCode;
  }
}
