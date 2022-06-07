import { Pipe, PipeTransform } from '@angular/core';
import {Question} from "../model/question/question.model";

@Pipe({
  name: 'questionFilterByIds',
  pure: false
})

export class QuestionFilterByIdsPipe implements PipeTransform {

  transform(questions: Question[], questionIds: number[]): any {
    questions = questions.filter(question => questionIds.includes(question.id as number));
    return questions;
  }

}
