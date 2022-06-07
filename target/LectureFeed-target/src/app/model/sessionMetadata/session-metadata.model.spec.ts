import { SessionMetadata } from './session-metadata.model';

describe('SessionMeta', () => {
  let sessionMeta = new SessionMetadata(99, "name", new Date());

  it('should create an instance', () => {
    expect(sessionMeta).toBeTruthy();
  });

});
