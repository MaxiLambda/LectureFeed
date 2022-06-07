export class Participant {
  private readonly _id: number;
  private readonly _nickname: string;
  private readonly _connected: boolean;

  constructor(id: number, nickname: string, connected: boolean) {
    this._id = id;
    this._nickname = nickname;
    this._connected = connected;
  }

  get id(): number {
    return this._id;
  }

  get nickname(): string {
    return this._nickname;
  }

  get connected(): boolean {
    return this._connected;
  }

}
