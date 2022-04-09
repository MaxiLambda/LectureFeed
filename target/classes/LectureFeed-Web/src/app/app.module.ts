import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {NotFoundComponent} from './component/pages/error/not-found/not-found.component';
import {FooterComponent} from './component/organisms/footer/footer.component';
import {HeaderComponent} from './component/organisms/header/header.component';
import {ParticipantLoginComponent} from './component/pages/participant/participant-login/participant-login.component';
import {ParticipantSessionComponent} from './component/pages/participant/participant-session/paricipant-session.component';
import {ContentComponent} from './component/organisms/content/content.component';
import {ToastModule} from "primeng/toast";
import {MessageService} from "primeng/api";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {PresenterManagementComponent} from "./component/pages/presenter/presenter-management/presenter-management.component";
import {PresenterSessionComponent} from "./component/pages/presenter/presenter-session/presenter-session.component";
import {AbstractSessionManagementComponent} from './component/organisms/abstract-session-management/abstract-session-management.component';
import {DialogModule} from "primeng/dialog";
import {ClipboardModule} from 'ngx-clipboard';
import {RippleModule} from "primeng/ripple";
import {ButtonModule} from "primeng/button";
import {CardModule} from 'primeng/card';
import {InputTextModule} from 'primeng/inputtext';
import {InputNumberModule} from 'primeng/inputnumber';
import {InputSwitchModule} from 'primeng/inputswitch';
import {InputTextareaModule} from 'primeng/inputtextarea';
import {ShareSessionCodeDialogComponent} from './component/organisms/share-session-code-dialog/share-session-code-dialog.component';
import {StoreModule} from "@ngrx/store";
import {adminCurrentSessionReducer} from "./state/admin/admin.token.reducer";
import {
  participantQuestionReducer,
  participantVotedQuestionReducer
} from "./state/participant/participant.reducer";
import {FormsModule} from "@angular/forms";
import {CopyrightComponent} from './component/pages/footerpages/copyright/copyright.component';
import {AboutUsComponent} from './component/pages/footerpages/about-us/about-us.component';
import {DataProtectionComponent} from './component/pages/footerpages/data-protection/data-protection.component';
import {ImprintComponent} from './component/pages/footerpages/imprint/imprint.component';
import {NewQuestionComponent} from './component/organisms/new-question/new-question.component';
import {CreateQuestionComponent} from './component/molecules/create-question/create-question.component';
import {TabViewModule} from 'primeng/tabview';
import {CheckboxModule} from 'primeng/checkbox';
import {TableModule} from 'primeng/table';
import {QuestionFilterByIdsPipe} from "./transform/QuestionFilterByIdsPipe";
import {AuthInterceptor} from "./lib/AuthInterceptor";
import {ParticipantQuestionTableComponent} from './component/organisms/participant-question-table/participant-question-table.component';
import {QuestionTableOverviewComponent} from "./component/molecules/question-table-overview/question-table-overview.component";
import {VotedQuestionByVotePipe} from "./transform/VotedQuestionByVotePipe";
import {UnvotedQuestionPipe} from "./transform/UnvotedQuestionPipe";
import {PresenterQuestionTableComponent} from './component/organisms/presenter-question-table/presenter-question-table.component';
import {QuestionFilterByClosedPipe} from "./transform/QuestionFilterByClosedPipe";
import {PresenterCreateSessionDialogComponent} from './component/organisms/presenter-create-session-dialog/presenter-create-session-dialog.component';
import {PresenterSessionMetadataOverviewComponent} from './component/organisms/presenter-session-metadata-overview/presenter-session-metadata-overview.component';
import {PresenterSessionDataComponent} from './component/pages/presenter/presenter-session-data/presenter-session-data.component';
import {AbstractActiveSessionManagementComponent} from './component/organisms/abstract-active-session-management/abstract-active-session-management.component';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {ConfirmationService} from 'primeng/api';
import {WaitDialogComponent} from './component/molecules/wait-dialog/wait-dialog.component';
import {ParticipantMoodSliderComponent} from './component/organisms/participant-mood-slider/participant-mood-slider.component';
import {NgxSliderModule} from '@angular-slider/ngx-slider';
import {NgxEchartsModule} from 'ngx-echarts';
import {MoodChartComponent} from './component/molecules/mood-chart/mood-chart.component';
import {PresenterSurveyComponent} from './component/organisms/presenter-survey/presenter-survey.component';
import {SurveySelectorDialogComponent} from './component/molecules/survey-selector-dialog/survey-selector-dialog.component';
import {SurveyFormComponent} from './component/molecules/survey-form/survey-form.component';
import {BackdropComponent} from './component/atoms/backdrop/backdrop.component';
import {SelectButtonModule} from 'primeng/selectbutton';
import {SurveyObjectFormComponent} from './component/molecules/survey-object-form/survey-object-form.component';
import {SurveyOverviewComponent} from './component/molecules/survey-overview/survey-overview.component';
import {DisplaySurveyEnumNamePipe} from "./transform/DisplaySurveyEnumNamePipe";
import {SurveyAnswerVisualizationComponent} from './component/molecules/survey-answer-visualization/survey-answer-visualization.component';
import {ListboxModule} from 'primeng/listbox';
import {DropdownModule} from 'primeng/dropdown';
import {MessagesModule} from 'primeng/messages';
import {MessageModule} from 'primeng/message';
import {SurveyLiveViewDialogComponent} from './component/molecules/survey-live-view-dialog/survey-live-view-dialog.component';
import {ProgressBarModule} from 'primeng/progressbar';
import {SurveyLiveAnswerDialogComponent} from './component/molecules/survey-live-answer-dialog/survey-live-answer-dialog.component';
import {SurveyAnswerEventVisualizationComponent} from './component/molecules/survey-answer-event-visualization/survey-answer-event-visualization.component';
import {SurveyResultVisualizationDialogComponent} from './component/molecules/survey-result-visualization-dialog/survey-result-visualization-dialog.component';
import {SidebarModule} from 'primeng/sidebar';
import {TooltipModule} from 'primeng/tooltip';
import {AcceptDialogComponent} from './component/molecules/accept-dialog/accept-dialog.component';
import {ParticipantTableComponent} from './component/molecules/participant-table/participant-table.component';
import {BadgeModule} from 'primeng/badge';
import { MoodDataChartComponent } from './component/molecules/mood-data-chart/mood-data-chart.component';
import {tokenReducer} from "./state/token/token.reducer";
import { ParticipantConnectionTableComponent } from './component/molecules/participant-connection-table/participant-connection-table.component';

@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
    FooterComponent,
    HeaderComponent,
    ParticipantLoginComponent,
    ParticipantSessionComponent,
    ContentComponent,
    PresenterManagementComponent,
    PresenterSessionComponent,
    AbstractSessionManagementComponent,
    ShareSessionCodeDialogComponent,
    CopyrightComponent,
    AboutUsComponent,
    DataProtectionComponent,
    ImprintComponent,
    NewQuestionComponent,
    CreateQuestionComponent,
    QuestionFilterByIdsPipe,
    QuestionTableOverviewComponent,
    ParticipantQuestionTableComponent,
    VotedQuestionByVotePipe,
    UnvotedQuestionPipe,
    PresenterQuestionTableComponent,
    QuestionFilterByClosedPipe,
    DisplaySurveyEnumNamePipe,
    PresenterCreateSessionDialogComponent,
    PresenterSessionMetadataOverviewComponent,
    PresenterSessionDataComponent,
    AbstractActiveSessionManagementComponent,
    WaitDialogComponent,
    ParticipantMoodSliderComponent,
    MoodChartComponent,
    PresenterSurveyComponent,
    SurveySelectorDialogComponent,
    SurveyFormComponent,
    BackdropComponent,
    SurveyObjectFormComponent,
    SurveyOverviewComponent,
    SurveyAnswerVisualizationComponent,
    SurveyLiveViewDialogComponent,
    SurveyLiveAnswerDialogComponent,
    SurveyAnswerEventVisualizationComponent,
    SurveyResultVisualizationDialogComponent,
    AcceptDialogComponent,
    ParticipantTableComponent,
    MoodDataChartComponent,
    ParticipantConnectionTableComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserModule,
    BrowserAnimationsModule,
    ToastModule,
    DialogModule,
    CardModule,
    ClipboardModule,
    InputTextModule,
    InputNumberModule,
    InputSwitchModule,
    TabViewModule,
    InputTextareaModule,
    CheckboxModule,
    TableModule,
    StoreModule.forRoot({
      token: tokenReducer,
      adminCurrentSessionData: adminCurrentSessionReducer,
      participantQuestionIds: participantQuestionReducer,
      participantVotedQuestions: participantVotedQuestionReducer
    }),
    RippleModule,
    ButtonModule,
    FormsModule,
    ConfirmDialogModule,
    NgxSliderModule,
    NgxEchartsModule.forRoot({
      echarts: () => import('echarts')
    }),
    SelectButtonModule,
    ListboxModule,
    DropdownModule,
    MessagesModule,
    MessageModule,
    ProgressBarModule,
    SidebarModule,
    TooltipModule,
    BadgeModule
  ],
  providers: [
    MessageService,
    ConfirmationService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
