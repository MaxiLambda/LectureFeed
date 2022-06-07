import {SessionData} from './session-data.model';
import {Question} from "../question/question.model";
import {Participant} from "../participant/participant.model";

describe('SessionData', () => {
  let question = new Question(99, new Participant(99, "nickname", false), "message", 0, new Date().getTime(), null);
  let participant = new Participant(99, "nickname", true);
  let sessionData = new SessionData([question], [participant]);

  it('should create an instance', () => {
    expect(sessionData).toBeTruthy();
  });

  it('get text', () => {
    expect(question.message).toEqual("message");
  });

});
