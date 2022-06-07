import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {SurveyTemplate, SurveyType} from "../../../model/surveyTemplate/survey-template.model";
import {$e} from "@angular/compiler/src/chars";
import {Options} from "@angular-slider/ngx-slider";

@Component({
  selector: 'app-survey-answer-event-visualization',
  templateUrl: './survey-answer-event-visualization.component.html',
  styleUrls: ['./survey-answer-event-visualization.component.scss']
})
export class SurveyAnswerEventVisualizationComponent implements OnChanges {

  @Input() template: SurveyTemplate|null = null
  @Input() value: string = "";
  @Output() valueChange: EventEmitter<string> = new EventEmitter();

  yesNoOptions = [
    {icon: 'pi pi-thumbs-down', value: "-1"},
    {icon: 'pi pi-thumbs-up', value: "1"}
  ];

  ratingOptions: Options = {
    floor: -5,
    ceil: 5
  };

  private isType(needed: SurveyType){
    return this.template?.type === needed;
  }

  isYesNoType(){
    return this.isType(SurveyType.YesNo);
  }

  isRatingType(){
    return this.isType(SurveyType.Rating);
  }

  isOpenAnswerType(){
    return this.isType(SurveyType.OpenAnswer);
  }

  ngOnChanges(changes: SimpleChanges) {
    if(changes.hasOwnProperty("template")){
      this.template = changes.template.currentValue;
    }
  }

  onUpdateYesNo($event: any) {
    this.updateValue($event.value.value);
  }

  onChangeOpenAnswer(value: string) {
    this.updateValue(value);
  }

  private updateValue(value: string){
    this.value = value;
    this.valueChange.emit(value);
  }

  userChangeEnd(changeContext: any) {
   this.updateValue(changeContext.value)
  }
}
