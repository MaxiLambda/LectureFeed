import {Injectable} from '@angular/core';
import {DefaultService} from "../defaultService/default.service";
import {Survey} from "../../model/survey/survey.model";
import {firstValueFrom, map} from "rxjs";
import {ISurveyTemplate, SurveyTemplate} from "../../model/surveyTemplate/survey-template.model";

@Injectable({
  providedIn: 'root'
})
export class SurveyService extends DefaultService{

  getSurveysBySessionId(sessionId: number){
    return firstValueFrom(this.http.get<Survey[]>(this.getAPIUrl()+`admin/session/${sessionId}/surveys`)
      .pipe(
        map((values: Survey[]) =>
          values.map(v => new Survey(v.id, new SurveyTemplate(v.template.id, v.template.name, v.template.type, v.template.question, v.template.duration, v.template.publishResults), v.answers, v.timestamp)))
      )
    );
  }

  getTemplatesBySessionId(sessionId: number){
    return firstValueFrom(this.http.get<SurveyTemplate[]>(this.getAPIUrl()+`admin/session/${sessionId}/survey/templates`)
      .pipe(
        map((values: SurveyTemplate[]) =>
          values.map(v => new SurveyTemplate(v.id, v.name, v.type, v.question, v.duration, v.publishResults)))
      ));
  }

  createByTemplate(sessionId: number, template: ISurveyTemplate){
    return firstValueFrom(this.http.post<SurveyTemplate>(this.getAPIUrl()+`admin/session/${sessionId}/survey/create`, template)
      .pipe(
        map((v: SurveyTemplate) =>  new SurveyTemplate(v.id, v.name, v.type, v.question, v.duration, v.publishResults))
      ));
  }

  createByTemplateId(sessionId: number, id: number){
    return firstValueFrom(this.http.get(this.getAPIUrl()+`admin/session/${sessionId}/survey/create/${id}`));
  }

  sendAnswer(sessionId: number, surveyId: number, value: string){
    return firstValueFrom(this.http.post(this.getAPIUrl()+`participant/session/${sessionId}/survey/${surveyId}/answer`, {text: value}));
  }

}
