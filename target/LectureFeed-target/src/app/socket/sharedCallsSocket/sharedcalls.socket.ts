import {DefaultSocket} from "../defaultSocket/default.socket";
import {Injectable} from "@angular/core";
import {map, Observable} from "rxjs";
import {SurveyTemplate} from "../../model/surveyTemplate/survey-template.model";
import {Survey} from "../../model/survey/survey.model";

@Injectable({
  providedIn: 'root'
})
export class SharedCallsSocket extends DefaultSocket {

  public onClose(sessionId: number): Observable<void>
  {
    return this.subscribe<void>(`/session/${sessionId}/onclose`);
  }

  protected castSurvey(v: Survey){
    return new Survey(v.id, this.castSurveyTemplate(v.template), v.answers, v.timestamp);
  }

  protected castSurveyTemplate(v: SurveyTemplate){
    return new SurveyTemplate(v.id, v.name, v.type, v.question, v.duration, v.publishResults);
  }

}
