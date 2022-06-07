import {Component} from '@angular/core';
import {Question} from "../../../model/question/question.model";
import {Participant} from "../../../model/participant/participant.model";
import {ActivatedRoute, Router} from "@angular/router";
import {MessageService} from "primeng/api";
import {SessionService} from "../../../service/sessionService/session.service";
import {AbstractSessionManagementComponent} from "../abstract-session-management/abstract-session-management.component";


@Component({ template: '' })
export class AbstractActiveSessionManagementComponent extends AbstractSessionManagementComponent{

  questions: Question[] = [];
  participants: Participant[] = [];

  constructor(
    protected router: Router,
    protected route: ActivatedRoute,
    protected messageService: MessageService,
    protected sessionService: SessionService,
  ){
    super(router, route, messageService);
  }

  protected validateSession(): Promise<void>
  {
    return new Promise(async (resolve, reject) =>{
      super.validateSession().then(() => {
        this.updateInitialData().then(() => resolve()).catch(err => reject(err));
      });
    });
  }

  protected updateInitialData(){
    return new Promise<void>(async (resolve, reject) =>{
      this.sessionService.getInitialData(this.sessionId as number).then(async sessionData => {
        if (sessionData !== null){
          this.questions = sessionData.questions;
          this.participants = sessionData.participants;
          this.startConnection(await this.getToken() as string);
        }
        resolve();
      }).catch(err => {
        this.displayErrorNotify(err.name, 'Failed to load session data');
        reject(err);
      });
    })
  }

  protected logOutSession(){
    this.questions = [];
    this.participants = [];
    super.logOutSession();
  }

  protected addQuestion(question: Question){
    this.questions = this.questions.filter(q => q.id !== question.id);
    this.questions.push(question);
  }

}

