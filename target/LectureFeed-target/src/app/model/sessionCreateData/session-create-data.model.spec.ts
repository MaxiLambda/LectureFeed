import { SessionCreateData } from './session-create-data.model';

describe('SessionCreateData', () => {
  it('should create an instance', () => {
    expect(new SessionCreateData(99, "99code")).toBeTruthy();
  });
});
