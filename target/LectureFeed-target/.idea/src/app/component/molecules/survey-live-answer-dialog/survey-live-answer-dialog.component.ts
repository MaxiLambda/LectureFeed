import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from '@angular/core';
import {SurveyTemplate} from "../../../model/surveyTemplate/survey-template.model";

@Component({
  selector: 'app-survey-live-answer-dialog',
  templateUrl: './survey-live-answer-dialog.component.html',
  styleUrls: ['./survey-live-answer-dialog.component.scss']
})
export class SurveyLiveAnswerDialogComponent implements OnChanges {

  @Input() template: SurveyTemplate|null = null;
  @Output() templateChange: EventEmitter<SurveyTemplate|null> = new EventEmitter();
  @Output() onClose: EventEmitter<string> = new EventEmitter();
  private currentTime: number = 0;
  private timer: NodeJS.Timeout | undefined;
  progressBarValue: number = 100;
  currentValue: string = "";

  constructor() { }

  isVisible(){
    return this.template !== null;
  }

  ngOnChanges(changes: SimpleChanges) {
    if(changes.hasOwnProperty("template")){
      if(changes.template.currentValue === null){
        this.template = null;
      }else{
        this.progressBarValue = 100;
        this.template = changes.template.currentValue;
        this.currentTime = 0;
        this.startTimer(this.template?.duration as number);
      }
    }
  }

  startTimer(maxTime: number){
    this.timer = setInterval(() => {
      if(this.currentTime >= maxTime){
        clearInterval(this.timer as NodeJS.Timeout);
        if(this.template !== null) this.onSend();
      }else{
        this.currentTime += 1;
        this.progressBarValue = 100 - this.currentTime / maxTime * 100;
      }
    }, 1000);
  }

  onSend() {
    this.template = null;
    this.onClose.emit(this.currentValue)
  }
}
