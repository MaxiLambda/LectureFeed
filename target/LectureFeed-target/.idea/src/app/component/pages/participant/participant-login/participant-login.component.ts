import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationService} from "../../../../service/authenticationService/authentication.service";
import {MessageService} from "primeng/api";
import {Store} from "@ngrx/store";
import {IAppParticipantState} from "../../../../state/participant/app.participant.state";
import {setToken} from "../../../../state/token/token.actions";

@Component({
  selector: 'app-participant-login',
  templateUrl: './participant-login.component.html',
  styleUrls: ['./participant-login.component.scss']
})
export class ParticipantLoginComponent{
  nickname = "";
  sessionCode = "";
  sessionId: number | null = null;
  selectedSessionId: number | null = null;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    protected messageService: MessageService,
    private readonly authenticationService: AuthenticationService,
    private readonly store: Store<IAppParticipantState>
    ) {
    let id =  parseInt(this.route.snapshot.paramMap.get('sessionId') as string);
    if(!isNaN(id)){
      this.sessionId = id;
    }
  }

  onSubmit(){
    if(this.sessionId === null){
      this.sessionId = this.selectedSessionId;
    }
    if(this.nickname.length > 0 && this.sessionCode.length > 0 && this.sessionId !== null){
      this.authenticationService.getParticipantToken(this.sessionId, this.nickname, this.sessionCode).then(token => {
        this.store.dispatch(setToken({token: token.token}));
        this.navigateToSessionById(this.sessionId as number);
      }).catch(() => {
        this.messageService.add({ severity: 'error', summary: 'Failed', detail: 'Login failed.', life: 4000 });
      });
    }
  }

  navigateToSessionById(sessionId: number){
    this.router.navigate(['participant/'+sessionId]);
  }


  onChangeNickname(){
    this.nickname = this.nickname.replace(/[^a-z0-9]/gi, '')
  }

  onChangeSessionCode(){
    this.sessionCode = this.sessionCode.replace(/[^a-z0-9]/gi, '').toUpperCase()
  }

}
