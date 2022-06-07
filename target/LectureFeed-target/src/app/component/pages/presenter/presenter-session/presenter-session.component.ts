import {Component, OnDestroy, OnInit} from '@angular/core';
import {Participant} from "../../../../model/participant/participant.model";
import {ActivatedRoute, Router} from "@angular/router";
import {ConfirmationService, MessageService} from "primeng/api";
import {AdminSocket} from "../../../../socket/adminSocket/admin.socket";
import {SessionService} from "../../../../service/sessionService/session.service";
import {
  IAbstractSessionManagementComponent
} from "../../../organisms/abstract-session-management/abstract-session-management.component";
import {select, Store} from "@ngrx/store";
import {IAppAdminState} from "../../../../state/admin/app.admin.state";
import {selectCurrentSessionData} from "../../../../state/admin/admin.selector";
import {take} from "rxjs/operators";
import {removeCurrentDataSession} from "../../../../state/admin/admin.actions";
import {firstValueFrom} from "rxjs";
import {Question} from "../../../../model/question/question.model";
import {
  AbstractActiveSessionManagementComponent
} from "../../../organisms/abstract-active-session-management/abstract-active-session-management.component";
import {WaitDialogService} from "../../../../service/waitDialogService/wait-dialog.service";
import {environment} from "../../../../../environments/environment";
import {AcceptDialogService} from "../../../../service/acceptDialogService/accept-dialog.service";
import {selectToken} from "../../../../state/token/token.selector";
import {CustomRouterService} from "../../../../service/customRouter/custom-router.service";
import {EnvironmentService} from "../../../../service/environmentService/environment.service";

const AVERAGE_LABEL = "Average";

@Component({
  selector: 'app-presenter-session',
  templateUrl: './presenter-session.component.html',
  styleUrls: ['./presenter-session.component.scss']
})
export class PresenterSessionComponent extends AbstractActiveSessionManagementComponent implements IAbstractSessionManagementComponent, OnInit, OnDestroy  {

  moodLineValues: {[key: string]: number} = {
    [AVERAGE_LABEL]: 0
  };

  sessionCode: string = ""
  displayShareCodeDialog: boolean = environment.displayShareSessionDialog
  visibleSidebar: boolean = false;

  constructor(
    protected readonly router: Router,
    protected readonly route: ActivatedRoute,
    protected readonly messageService: MessageService,
    protected readonly sessionService: SessionService,
    public readonly adminSocket: AdminSocket,
    private readonly store: Store<IAppAdminState>,
    private readonly waitDialogService: WaitDialogService,
    private readonly confirmationService: ConfirmationService,
    private readonly acceptDialogService: AcceptDialogService,
    private readonly customRouterService: CustomRouterService
  ) {
    super(router, route, messageService, sessionService);
  }

  ngOnInit(){
    this.validateSession().then(_ => {
      firstValueFrom(this.store.pipe(select(selectCurrentSessionData), take(1))).then(sessionData => {
        this.sessionCode = sessionData.sessionCode;
      });
    });
  }

  ngOnDestroy() {
    this.adminSocket.disconnect();
    this.displayShareCodeDialog = false;
    this.visibleSidebar = false;
    this.sessionCode = "";
  }

  protected getToken()
  {
    return firstValueFrom(this.store.pipe(select(selectToken), take(1)))
  }

  public startConnection(token: string){
    this.connectToSocket(token);
  }

  private connectToSocket(token: string){
    this.waitDialogService.open("Wait for connection");
    this.adminSocket.connect(token).subscribe((next) => {
      if(next instanceof Error){
        this.acceptDialogService.open("Connection lost", "Session are closed.").then(() => {
          this.logOutSession();
        });
      }else {
        this.waitDialogService.close();
        this.adminSocket.onJoinParticipant(this.getSessionId()).subscribe(p => this.onJoinParticipant(p));
        this.adminSocket.onUpdateQuestion(this.getSessionId()).subscribe(q => this.addQuestion(q))
        this.adminSocket.onUpdateMood(this.getSessionId()).subscribe(value => this.updateMoodAverageLineChart(value));
        this.adminSocket.onClose(this.getSessionId()).subscribe(value => this.onCloseSession());
        this.adminSocket.onParticipantConnectionStatus(this.getSessionId()).subscribe(p => this.updateParticipants(p))
      }
    });
  }

  private onJoinParticipant(participant: Participant){
    this.participants.push(participant);
    this.displayNotify({ severity: 'info', summary: 'User joined', detail: participant.nickname, life: 2000 });
  }

  public navigateToLogin(){
    this.customRouterService.navigateWithObserverQueryParams(['/presenter']);
  }

  onClickCloseSession(){
    this.visibleSidebar = false;
    this.confirmationService.confirm({
      header: 'Close Session',
      message: 'Are you sure you want to finish the session?',
      accept: () => {
        this.sessionService.closeSession(this.getSessionId());
        this.logOutSession();
      }
    });
  }

  private onCloseSession() {
    this.logOutSession();
  }

  protected logOutSession(){
    this.adminSocket?.disconnect();
    this.store.dispatch(removeCurrentDataSession());
    super.logOutSession();
  }

  onHideShareCodeDialog(){
    this.displayShareCodeDialog = false;
  }

  onClosedQuestion(question: Question) {
    this.adminSocket.closeQuestion(this.sessionId as number, question);
  }

  updateMoodAverageLineChart(value: number){
    this.moodLineValues[AVERAGE_LABEL] = value;
  }

  onClickOpenWelcomeDialog() {
    this.visibleSidebar = false;
    this.displayShareCodeDialog = true;
  }

  private updateParticipants(participants: Participant[]) {
    this.participants = participants
  }

  onKillParticipant(participant: Participant) {
    this.confirmationService.confirm({
      header: `Disconnect Participant (${participant.nickname})`,
      message: "Do you really want to deregister the participant?",
      accept: () => {
        setTimeout(() => {
          this.confirmationService.confirm({
            header: `Block Participant (${participant.nickname})`,
            message: "Should the participant be blocked?",
            accept: () => {
              this.sessionService.killParticipant(this.getSessionId(), participant.id, true);
            },
            reject: () =>{
              this.sessionService.killParticipant(this.getSessionId(), participant.id, false);
            }
          });
        }, 1000);
      }
    });
  }

}
