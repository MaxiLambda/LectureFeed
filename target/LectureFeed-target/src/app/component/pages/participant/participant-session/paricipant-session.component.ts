import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Participant} from "../../../../model/participant/participant.model";
import {ParticipantSocket} from "../../../../socket/participantSocket/participant.socket";
import {ConfirmationService, MessageService} from "primeng/api";
import {SessionService} from "../../../../service/sessionService/session.service";
import {
  IAbstractSessionManagementComponent
} from "../../../organisms/abstract-session-management/abstract-session-management.component";
import {firstValueFrom} from "rxjs";
import {select, Store} from "@ngrx/store";
import {take} from "rxjs/operators";
import {IAppParticipantState, IVotedQuestion} from "../../../../state/participant/app.participant.state";
import {
  selectParticipantData,
  selectQuestionIds,
  selectVotedQuestions
} from "../../../../state/participant/participant.selector";
import {
  deleteVotedQuestion,
  pushQuestionId,
  votedQuestion
} from "../../../../state/participant/participant.actions";
import {Question} from "../../../../model/question/question.model";
import {
  IQuestionMessageTemplate
} from "../../../molecules/create-question/create-question.component";
import {
  AbstractActiveSessionManagementComponent
} from "../../../organisms/abstract-active-session-management/abstract-active-session-management.component";
import {WaitDialogService} from "../../../../service/waitDialogService/wait-dialog.service";
import {SurveyTemplate} from "../../../../model/surveyTemplate/survey-template.model";
import {SurveyService} from "../../../../service/surveyService/survey.service";
import {Survey} from "../../../../model/survey/survey.model";
import {removeCurrentDataSession} from "../../../../state/admin/admin.actions";
import {AcceptDialogService} from "../../../../service/acceptDialogService/accept-dialog.service";
import {selectToken} from "../../../../state/token/token.selector";
import {removeToken} from "../../../../state/token/token.actions";

const AVERAGE_LABEL = "Average";
const MY_MOOD_LABEL = "You";

@Component({
  selector: 'app-paricipant-session',
  templateUrl: './paricipant-session.component.html',
  styleUrls: ['./paricipant-session.component.scss']
})
export class ParticipantSessionComponent extends AbstractActiveSessionManagementComponent implements IAbstractSessionManagementComponent, OnInit, OnDestroy  {

  nickname: string = "";
  participantId: number = 0;
  questionIds: number[] = [];
  votedQuestions: IVotedQuestion[] = [];
  moodLineValues: {[key: string]: number} = {
    [AVERAGE_LABEL]: 0,
    [MY_MOOD_LABEL]: 0
  };
  currentSurvey: Survey|null = null;
  currentSurveyId: number|null = null;
  currentSurveyTemplate: SurveyTemplate|null = null;
  visibleSidebar: boolean = false;

  constructor(
    protected readonly router: Router,
    protected readonly route: ActivatedRoute,
    protected readonly messageService: MessageService,
    protected readonly sessionService: SessionService,
    protected readonly participantSocket: ParticipantSocket,
    private readonly store: Store<IAppParticipantState>,
    private readonly waitDialogService: WaitDialogService,
    private readonly surveyService: SurveyService,
    private readonly confirmationService: ConfirmationService,
    private readonly acceptDialogService: AcceptDialogService
  ){
    super(router, route, messageService, sessionService);
  }

  ngOnInit() {
    this.validateSession();
  }

  ngOnDestroy() {
    this.participantSocket.disconnect();
  }

  protected getToken()
  {
    return firstValueFrom(this.store.pipe(select(selectToken), take(1)));
  }

  private loadData(){
    firstValueFrom(this.store.pipe(select(selectParticipantData), take(1))).then(participantData => {
      this.nickname = participantData?.nickname
      this.participantId = participantData?.id;
    });
    firstValueFrom(this.store.pipe(select(selectQuestionIds), take(1))).then(questionIds => {
      this.questionIds = Array.from(questionIds);
    });
    this.store.select(selectVotedQuestions).subscribe(votes => {
      this.votedQuestions = votes;
    });
  }

  public startConnection(token: string){
    this.loadData();
    this.store.select(selectQuestionIds).subscribe(questionIds => {
      if(questionIds !== undefined) questionIds.map(id => this.questionIds.push(id));
    });
    this.connectToSocket(token);
  }

  public navigateToLogin(){
    let path = 'participant/join/';
    if(this.sessionId !== null) path += this.sessionId;
    this.router.navigate([path]);
  }

  private connectToSocket(token: string){
    this.waitDialogService.open("Wait for connection");
    this.participantSocket.connect(token, false).subscribe((next) => {
      if(next instanceof Error){
        this.acceptDialogService.open("Connection lost", "Session are closed.").then(() => {
          this.logOutSession();
        });
      }else {
        this.waitDialogService.close();
        this.participantSocket.onJoinParticipant(this.getSessionId()).subscribe(p => this.onJoinParticipant(p));
        this.participantSocket.onUpdateQuestion(this.getSessionId()).subscribe(q => this.addQuestion(q));
        this.participantSocket.onUpdateMood(this.getSessionId()).subscribe(value => this.updateMoodAverageLineChart(value));
        this.participantSocket.onCreateSurvey(this.getSessionId()).subscribe(t => this.onCreateSurvey(t.surveyId, t.surveyTemplate));
        this.participantSocket.onClose(this.getSessionId()).subscribe(() => this.onCloseSession());
      }
    });
  }

  private onJoinParticipant(participant: Participant){
    this.participants.push(participant);
    this.displayNotify({ severity: 'info', summary: 'User joined', detail: participant.nickname, life: 2000 });
  }

  private addOwnQuestion(question: Question){
    this.store.dispatch(pushQuestionId({questionId: question.id as number}));
  }

  onClickLogout(){
    this.visibleSidebar = false;
    this.confirmationService.confirm({
      header: 'Leave session',
      message: 'Are you sure you want to leave the session?',
      accept: () => {
        this.logOutSession();
      }
    });
  }

  private onCloseSession() {
    this.acceptDialogService.open("Session closed", "This session was closed by the organizer.").then(() => {
      this.logOutSession();
    });
  }

  protected logOutSession(){
    this.store.dispatch(removeToken());
    this.store.dispatch(deleteVotedQuestion())
    super.logOutSession();
  }

  onCreatedQuestionTemplate(createdQuestion: IQuestionMessageTemplate) {
    this.sessionService.createQuestion(this.sessionId as number, {
        participantId: this.participantId,
        message: createdQuestion.message,
        anonymous: createdQuestion.anonymous,
        created: new Date().getTime()
    }).then(question => this.addOwnQuestion(question))
  }

  onVotedQuestion(vote: IVotedQuestion) {
    this.store.dispatch(votedQuestion({ votedQuestion: vote }));
    this.participantSocket.voteQuestionId(this.sessionId as number, vote.questionId, vote.vote);
  }

  onSliderChange(value: number) {
    this.moodLineValues[MY_MOOD_LABEL] = value;
    this.participantSocket.sendMood(this.getSessionId() as number, value);
  }

  onCreateSurvey(surveyId: number, surveyTemplate: SurveyTemplate){
    this.currentSurvey = null;
    this.currentSurveyId = surveyId;
    this.currentSurveyTemplate = surveyTemplate;
    this.participantSocket.onSurveyResult(this.sessionId as number, this.currentSurveyId).subscribe(survey => {
      this.currentSurvey = survey;
      this.currentSurveyTemplate = null;
    });
  }

  updateMoodAverageLineChart(value: number){
    this.moodLineValues[AVERAGE_LABEL] = value;
  }

  onCloseSurveyLiveAnswerDialog(value: string) {
    if(value.length > 0 && this.currentSurveyTemplate !== null && this.currentSurveyId !== null){
      this.surveyService.sendAnswer(this.getSessionId() as number, this.currentSurveyId, value).then(() =>{
        this.messageService.add({ severity: 'success', summary: 'Successfully', detail: 'Data has been sent.', life: 2000 })
      })
    }
  }

  onCloseSurveyResult() {
    this.currentSurvey = null;
  }



}


