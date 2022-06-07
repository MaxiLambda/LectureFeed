import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {ISurveyTemplate, SurveyTemplate, SurveyType} from "../../../model/surveyTemplate/survey-template.model";

enum Tabs {
  NewSurvey,
  SelectSurvey
}

@Component({
  selector: 'app-survey-selector-dialog',
  templateUrl: './survey-selector-dialog.component.html',
  styleUrls: ['./survey-selector-dialog.component.scss']
})
export class SurveySelectorDialogComponent implements OnInit, OnChanges {

  @Input() visible: boolean = false;
  @Output() visibleChange: EventEmitter<boolean> = new EventEmitter();
  @Input() templates: SurveyTemplate[] = [];
  @Output() onCreateTemplate: EventEmitter<ISurveyTemplate> = new EventEmitter();
  @Output() onSelectTemplate: EventEmitter<number> = new EventEmitter();

  template: ISurveyTemplate = {
    name: "",
    type: SurveyType.YesNo,
    duration: 5,
    question: "",
    publishResults: true
  };
  activeIndex: Tabs = Tabs.NewSurvey;
  disabledSubmit: boolean = true;
  selectedTemplate: SurveyTemplate|null = null;

  ngOnInit(): void {
    if(this.templates.length > 0){
      this.selectedTemplate = this.templates[0];
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    if(changes.hasOwnProperty("templates")){
      this.templates = changes.templates.currentValue;
      if(this.templates.length > 0){
        this.selectedTemplate = this.templates[0];
      }
    }
  }

  onSubmit() {
    if(this.activeIndex === Tabs.NewSurvey){
      this.onCreateTemplate.emit(this.template);
    }else if(this.activeIndex === Tabs.SelectSurvey && this.selectedTemplate !== null){
      this.onSelectTemplate.emit(this.selectedTemplate.id);
    }
    this.onHide();
  }

  onTemplateValid(valid: boolean) {
    this.disabledSubmit = !valid;
  }

  onHide() {
    this.visible = false;
    this.visibleChange.emit(this.visible);
  }


}
