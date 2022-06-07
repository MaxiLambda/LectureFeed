import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Question} from "../../../model/question/question.model";
import {ICustomColumnHeader} from "../../molecules/question-table-overview/question-table-overview.component";

@Component({
  selector: 'app-presenter-question-table',
  templateUrl: './presenter-question-table.component.html',
  styleUrls: ['./presenter-question-table.component.scss']
})
export class PresenterQuestionTableComponent {

  @Input() questions: Question[] = [];
  @Output() onClosedQuestion: EventEmitter<Question> = new EventEmitter();
  customColumnHeaders: ICustomColumnHeader[] = [
    {
      title: "Action"
    }
  ]

  close(question: Question) {
    this.onClosedQuestion.emit(question)
  }

}
