export interface ISessionMetadata{
  sessionId: number,
  name: string,
  closed: Date
}

export class SessionMetadata implements ISessionMetadata{
  private readonly _sessionId: number
  private readonly _name: string
  private readonly _closed: Date

  constructor(sessionId: number, name: string, closed: Date) {
    this._sessionId = sessionId;
    this._name = name;
    this._closed = closed;
  }

  get sessionId(): number {
    return this._sessionId;
  }

  get name(): string {
    return this._name;
  }

  get closed(): Date {
    return this._closed;
  }

}
