import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Survey} from "../../../model/survey/survey.model";

@Component({
  selector: 'app-survey-result-visualization-dialog',
  templateUrl: './survey-result-visualization-dialog.component.html',
  styleUrls: ['./survey-result-visualization-dialog.component.scss']
})
export class SurveyResultVisualizationDialogComponent implements OnChanges{

  @Input() survey: Survey|null = null;
  @Output() templateChange: EventEmitter<Survey|null> = new EventEmitter();
  @Output() onClose: EventEmitter<void> = new EventEmitter();

  visible:boolean = false;

  ngOnChanges(changes: SimpleChanges) {
    if(changes.hasOwnProperty("survey")){
      this.visible = changes.survey.currentValue !== null;
    }
  }

}
