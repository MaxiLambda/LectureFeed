import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Question} from "../../../model/question/question.model";
import {IQuestionMessageTemplate} from "../../molecules/create-question/create-question.component";

@Component({
  selector: 'app-new-question',
  templateUrl: './new-question.component.html',
  styleUrls: ['./new-question.component.scss']
})
export class NewQuestionComponent{

  @Input() questions: Question[] = [];
  @Output() onCreatedQuestion: EventEmitter<IQuestionMessageTemplate> = new EventEmitter();


}
