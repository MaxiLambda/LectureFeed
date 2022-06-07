export class MoodEntity {
  private readonly _id: number
  private readonly _value: number
  private readonly _timestamp: number

  constructor(id: number, value: number, timestamp: number) {
    this._id = id;
    this._value = value;
    this._timestamp = timestamp;
  }

  get id(): number {
    return this._id;
  }

  get value(): number {
    return this._value;
  }

  get timestamp(): number {
    return this._timestamp;
  }

}
