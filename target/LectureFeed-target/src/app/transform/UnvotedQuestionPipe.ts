import { Pipe, PipeTransform } from '@angular/core';
import {Question} from "../model/question/question.model";
import {IVotedQuestion} from "../state/participant/app.participant.state";

@Pipe({
  name: 'unvotedQuestion',
  pure: false
})

export class UnvotedQuestionPipe implements PipeTransform {

  transform(question: Question, votedQuestions: IVotedQuestion[]): boolean {
    return votedQuestions.filter(vote => vote.questionId === question.id).length === 0;
  }

}
