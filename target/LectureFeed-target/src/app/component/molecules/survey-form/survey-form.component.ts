import {Component, EventEmitter, Input, Output} from '@angular/core';
import {SurveyType} from "../../../model/surveyTemplate/survey-template.model";


@Component({
  selector: 'app-survey-form',
  templateUrl: './survey-form.component.html',
  styleUrls: ['./survey-form.component.scss']
})
export class SurveyFormComponent {
  typeOptions = [
    {name: 'Yes No', value: SurveyType.YesNo},
    {name: 'Rating', value: SurveyType.Rating},
    {name: 'Open Answer', value: SurveyType.OpenAnswer}
  ];
  @Input() name: string = "";
  @Output() nameChange: EventEmitter<string> = new EventEmitter<string>();
  @Input() type: number = 0;
  @Output() typeChange: EventEmitter<number> = new EventEmitter<number>();
  @Input() question: string = "";
  @Output() questionChange: EventEmitter<string> = new EventEmitter<string>();
  @Input() duration: number = 5;
  @Output() durationChange: EventEmitter<number> = new EventEmitter<number>();
  @Input() publishResults: boolean = true;
  @Output() publishResultsChange: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Input() disabledForm: boolean = false;
  @Output() onValid: EventEmitter<boolean> = new EventEmitter();

  private isFormValid(): boolean{
    return this.name.length > 0 && this.type >= 0 && this.question.length > 0 && this.duration >= 5;
  }

  onChange() {
    this.nameChange.emit(this.name);
    this.typeChange.emit(this.type);
    this.questionChange.emit(this.question);
    this.durationChange.emit(this.duration);
    this.publishResultsChange.emit(this.publishResults);
    this.onValid.emit(this.isFormValid());
  }

}
