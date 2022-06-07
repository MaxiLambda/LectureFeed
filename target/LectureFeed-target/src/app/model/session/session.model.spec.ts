import { Session } from './session.model';

describe('Session', () => {
  it('should create an instance', () => {
    expect(new Session(1, "name", "code", [], [],{}, {}, new Date().getTime())).toBeTruthy();
  });
});
