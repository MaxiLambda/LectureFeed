import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ISurveyTemplate, SurveyType} from "../../../model/surveyTemplate/survey-template.model";

@Component({
  selector: 'app-survey-object-form',
  templateUrl: './survey-object-form.component.html',
  styleUrls: ['./survey-object-form.component.scss']
})
export class SurveyObjectFormComponent {

  @Input() template: ISurveyTemplate = {
    name: "",
    type: SurveyType.YesNo,
    duration: 5,
    question: "",
    publishResults: true
  };
  @Output() templateChange: EventEmitter<ISurveyTemplate> = new EventEmitter();
  @Output() onValid: EventEmitter<boolean> = new EventEmitter();
  @Input() disabledForm: boolean = false;


  onChange(valid: boolean) {
    this.templateChange.emit(this.template);
    this.onValid.emit(valid);
  }
}
