import { Pipe, PipeTransform } from '@angular/core';
import {Question} from "../model/question/question.model";
import {IVotedQuestion} from "../state/participant/app.participant.state";

@Pipe({
  name: 'votedQuestionByVote',
  pure: false
})

export class VotedQuestionByVotePipe implements PipeTransform {

  transform(question: Question, votedQuestions: IVotedQuestion[], expect: boolean): boolean {
    return votedQuestions.filter(vote => vote.questionId === question.id && expect === vote.vote).length > 0;
  }

}
