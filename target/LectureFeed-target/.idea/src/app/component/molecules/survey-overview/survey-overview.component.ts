import {Component, Input, OnInit} from '@angular/core';
import {Survey} from "../../../model/survey/survey.model";

@Component({
  selector: 'app-survey-overview',
  templateUrl: './survey-overview.component.html',
  styleUrls: ['./survey-overview.component.scss']
})
export class SurveyOverviewComponent {

  @Input() surveys: Survey[] = [];
  @Input() displayCaption: boolean = true;

}
