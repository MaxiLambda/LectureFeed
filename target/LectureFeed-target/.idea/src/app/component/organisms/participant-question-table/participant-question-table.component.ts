import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Question} from "../../../model/question/question.model";
import {ICustomColumnHeader} from "../../molecules/question-table-overview/question-table-overview.component";
import {IVotedQuestion} from "../../../state/participant/app.participant.state";

@Component({
  selector: 'app-participant-question-table',
  templateUrl: './participant-question-table.component.html',
  styleUrls: ['./participant-question-table.component.scss']
})
export class ParticipantQuestionTableComponent{

  @Input() questions: Question[] = [];
  @Input() votedQuestions: IVotedQuestion[] = [];
  @Output() onVotedQuestion: EventEmitter<IVotedQuestion> = new EventEmitter();
  customColumnHeaders: ICustomColumnHeader[] = [
    {
      title: "Vote"
    }
  ]

  vote(question: Question, b: boolean) {
    this.onVotedQuestion.emit({questionId: question.id as number, vote: b})
  }

}
