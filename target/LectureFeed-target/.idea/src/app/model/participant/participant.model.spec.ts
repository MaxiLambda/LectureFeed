import {Participant} from './participant.model';

describe('Participant', () => {
  const participant = new Participant(99, "nickname", true);
  it('should create an instance', () => {
    expect(participant).toBeTruthy();
  });
  it('get attribute id', () => {
    expect(participant.id).toEqual(99);
  });
  it('get attribute nickname', () => {
    expect(participant.nickname).toEqual("nickname");
  });
});
