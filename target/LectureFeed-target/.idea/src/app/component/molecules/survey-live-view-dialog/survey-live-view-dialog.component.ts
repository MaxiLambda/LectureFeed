import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from '@angular/core';
import {Survey} from "../../../model/survey/survey.model";
import {SurveyTemplate} from "../../../model/surveyTemplate/survey-template.model";

@Component({
  selector: 'app-survey-live-view-dialog',
  templateUrl: './survey-live-view-dialog.component.html',
  styleUrls: ['./survey-live-view-dialog.component.scss']
})
export class SurveyLiveViewDialogComponent implements OnChanges {

  @Input() displayResult: boolean = true;
  @Output() displayResultChange: EventEmitter<boolean> = new EventEmitter();
  @Input() template: SurveyTemplate|null = null;
  @Output() templateChange: EventEmitter<SurveyTemplate|null> = new EventEmitter();
  @Input() survey: Survey|null = null;
  @Output() surveyChange: EventEmitter<Survey|null> = new EventEmitter();
  @Output() onClose: EventEmitter<void> = new EventEmitter();
  private currentTime: number = 0;
  private timer: NodeJS.Timeout | undefined;
  progressBarValue: number = 100;

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
      }else{
        this.currentTime += 1;
        this.progressBarValue = 100 - this.currentTime / maxTime * 100;
      }
    }, 1000);
  }


  onHide() {
    this.onClose.emit()
  }
}
