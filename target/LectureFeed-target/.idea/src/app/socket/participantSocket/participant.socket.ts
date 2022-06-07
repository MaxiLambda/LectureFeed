import {Question} from "../../model/question/question.model";
import {Injectable} from "@angular/core";
import {map, Observable} from "rxjs";
import {Participant} from "../../model/participant/participant.model";
import {Survey} from "../../model/survey/survey.model";
import {SharedCallsSocket} from "../sharedCallsSocket/sharedcalls.socket";
import {SurveyTemplate} from "../../model/surveyTemplate/survey-template.model";

@Injectable({
  providedIn: 'root'
})
export class ParticipantSocket extends SharedCallsSocket {

  public voteQuestionId(sessionId: number, questionId: number, vote: boolean){
    let path = vote ? `/participant/session/${sessionId}/question/${questionId}/rating/up`: `/participant/session/${sessionId}/question/${questionId}/rating/down`
    this.getStompClient().send(path, {});
  }

  public onUpdateQuestion(sessionId: number): Observable<Question>
  {
    return this.subscribe<Question>(`/participant/session/${sessionId}/question/onupdate`).pipe(
      map((data: Question) =>
        new Question(
          data.id,
          new Participant(data.participant.id, data.participant.nickname, data.participant.connected),
          data.message,
          data.rating,
          data.created,
          data.closed
          ))
    );
  }

  public onJoinParticipant(sessionId: number): Observable<Participant>
  {
    return this.subscribe<Participant>(`/participant/session/${sessionId}/user/onjoin`);
  }

  public sendMood(sessionId: number, value: number){
    this.getStompClient().send(`/participant/session/${sessionId}/mood/${value}`, {});
  }

  public onUpdateMood(sessionId: number){
    return this.subscribe<number>(`/participant/session/${sessionId}/mood/onupdate`);
  }

  public onSurveyResult(sessionId: number, surveyId: number){
    return this.subscribe<Survey>(`/participant/session/${sessionId}/survey/${surveyId}/onresult`)
      .pipe(
        map(v => this.castSurvey(v))
      );
  }

  public onCreateSurvey(sessionId: number): Observable<{surveyId: number, surveyTemplate: SurveyTemplate}>
  {
    return this.subscribe<{surveyId: number, surveyTemplate: SurveyTemplate}>(`/participant/session/${sessionId}/survey/oncreate`).pipe(
      map((data: {surveyId: number, surveyTemplate: SurveyTemplate}) =>  ({surveyId: data.surveyId, surveyTemplate: this.castSurveyTemplate(data.surveyTemplate)}))
    );
  }

}
