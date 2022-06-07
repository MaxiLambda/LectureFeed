import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Survey} from "../../../model/survey/survey.model";
import {SurveyService} from "../../../service/surveyService/survey.service";
import {ISurveyTemplate, SurveyTemplate, SurveyType} from "../../../model/surveyTemplate/survey-template.model";
import {AdminSocket} from "../../../socket/adminSocket/admin.socket";

@Component({
  selector: 'app-presenter-survey',
  templateUrl: './presenter-survey.component.html',
  styleUrls: ['./presenter-survey.component.scss']
})
export class PresenterSurveyComponent implements OnInit, OnChanges {

  @Input() sessionId: number = 0;
  @Input() adminSocket: AdminSocket|null = null;
  templates: SurveyTemplate[] = [];
  surveys: Survey[] = [];
  visibleSelectorDialog: boolean = false;

  currentSurveyTemplate: SurveyTemplate|null = null;
  currentSurvey: Survey|null = null;
  currentSurveyDisplayResult: boolean = false;
  private connected: boolean = false;

  constructor(
    private readonly surveyService: SurveyService,
  ){}

  ngOnChanges(changes: SimpleChanges) {
    if(changes.hasOwnProperty("adminSocket")){
      this.adminSocket = changes.adminSocket.currentValue;
    }
  }

  ngOnInit(): void {
    this.adminSocket?.waitForConnection().subscribe(next => {
      if(!(next instanceof Error) && !this.connected){
        this.adminSocket?.onCreateSurvey(this.sessionId).subscribe(survey =>{
          this.currentSurveyTemplate = survey.template;
          this.currentSurvey = survey;
          this.currentSurveyDisplayResult = false;
          this.adminSocket?.onUpdateSurvey(this.sessionId, this.currentSurvey.id).subscribe(survey =>{
            this.currentSurvey = survey;
          });
          this.adminSocket?.onSurveyResult(this.sessionId, this.currentSurvey.id).subscribe(survey =>{
            this.currentSurvey = survey;
            this.currentSurveyDisplayResult = true;
            this.surveys = [...this.surveys, survey];
          });
        });
      }
    });

    this.surveyService.getTemplatesBySessionId(this.sessionId).then(templates => {
      this.templates = templates;
    });
    this.surveyService.getSurveysBySessionId(this.sessionId).then(surveys => {
      this.surveys = surveys;
    });
  }

  onClickNew() {
    this.visibleSelectorDialog = true
  }

  onCreateSurveyByTemplate(templ: ISurveyTemplate) {
    this.surveyService.createByTemplate(this.sessionId, templ).then(template => {
      this.templates = [...this.templates, template];
    });
  }

  onCreateBySelectTemplateId(id: number) {
    this.surveyService.createByTemplateId(this.sessionId, id);
  }

  onCloseCurrentSurvey() {
    this.currentSurveyTemplate = null;
    this.currentSurvey = null;
  }
}
