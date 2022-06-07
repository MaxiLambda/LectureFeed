import { EnvironmentInfo } from './environment-info.model';

describe('EnvironmentInfo', () => {
  it('should create an instance', () => {
    expect(new EnvironmentInfo("192.168.0.2")).toBeTruthy();
  });
});
