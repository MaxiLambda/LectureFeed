import {Question} from './question.model';
import {Participant} from "../participant/participant.model";

describe('Question', () => {
  let question = new Question(99, new Participant(99, "nickname", false), "message", 0, new Date().getTime(), null);

  it('should create an instance', () => {
    expect(question).toBeTruthy();
  });

  it('get message', () => {
    expect(question.message).toEqual("message");
  });

});
