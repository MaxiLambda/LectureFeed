import { MoodEntity } from './mood-entity.model';

describe('MoodEntity', () => {
  it('should create an instance', () => {
    expect(new MoodEntity(99, 99, new Date().getTime())).toBeTruthy();
  });
});
