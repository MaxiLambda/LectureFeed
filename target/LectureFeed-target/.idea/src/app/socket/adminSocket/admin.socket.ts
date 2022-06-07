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
export class AdminSocket extends SharedCallsSocket {

  public onQuestion(): Observable<Question>
  {
    return this.subscribe<Question>('/admin/question')
  }

  public onJoinParticipant(sessionId: number): Observable<Participant>
  {
    return this.subscribe<Participant>(`/admin/session/${sessionId}/user/onjoin`);
  }

  public onParticipantConnectionStatus(sessionId: number)
  {
    return this.subscribe<Participant[]>(`/admin/session/${sessionId}/participant/connections/status`);
  }

  public closeQuestion(sessionId: number, question: Question){
    this.getStompClient().send(`/admin/session/${sessionId}/question/${question.id}/close`);
  }

  public onUpdateQuestion(sessionId: number): Observable<Question>
  {
    return this.subscribe<Question>(`/admin/session/${sessionId}/question/onupdate`);
  }

  public onUpdateMood(sessionId: number){
    return this.subscribe<number>(`/admin/session/${sessionId}/mood/onupdate`);
  }

  public onUpdateSurvey(sessionId: number, surveyId: number): Observable<Survey>
  {
    return this.subscribe<Survey>(`/admin/session/${sessionId}/survey/${surveyId}/onupdate`)
      .pipe(
        map(v => this.castSurvey(v))
      );
  }

  public onSurveyResult(sessionId: number, surveyId: number){
    return this.subscribe<Survey>(`/admin/session/${sessionId}/survey/${surveyId}/onresult`)
      .pipe(
        map(v => this.castSurvey(v))
      );
  }

  public onCreateSurvey(sessionId: number): Observable<Survey>
  {
    return this.subscribe<Survey>(`/admin/session/${sessionId}/survey/oncreate`).pipe(
      map((v: Survey) =>  this.castSurvey(v))
    );
  }

}
