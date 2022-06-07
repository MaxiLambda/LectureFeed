import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MessageService, Message} from "primeng/api";

export interface IAbstractSessionManagementComponent{
  navigateToLogin(): void;
  startConnection(token: string): void;
}

@Component({ template: '' })
export class AbstractSessionManagementComponent implements IAbstractSessionManagementComponent {

  sessionId: number|null = null;

  constructor(
    protected router: Router,
    protected route: ActivatedRoute,
    protected messageService: MessageService
  ){}

  protected validateSession(): Promise<void>
  {
    return new Promise(async (resolve, reject) =>{
      let id = this.route.snapshot.paramMap.get('sessionId');
      this.sessionId = id ? parseInt(id) : null;
      let token: string | null = await this.getToken();
      if(this.sessionId===null || token === null || token.trim().length === 0){
        this.navigateToLogin();
      }else {
        resolve();
      }
    })
  }

  public getSessionId(){
    return this.sessionId as number;
  }

  protected getToken(): Promise<string|null>{
    return Promise.resolve(null);
  }

  protected logOutSession(){
    this.navigateToLogin();
    this.sessionId = null;
  }

  public navigateToLogin(){}

  public startConnection(token: string){}

  protected displayNotify(message: Message){
    this.messageService.add(message);
  }

  protected displayErrorNotify(detail: string, summary = "Fatal Error"){
    this.displayNotify({ severity: 'error', summary: summary, detail: detail, life: 4000 });
  }

  protected displayErrorObjectNotify(err: Error){
    this.displayErrorNotify(err.name);
  }

}
