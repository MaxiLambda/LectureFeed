import { Pipe, PipeTransform } from '@angular/core';
import {Question} from "../model/question/question.model";

@Pipe({
  name: 'questionFilterByClosed',
  pure: false
})

export class QuestionFilterByClosedPipe implements PipeTransform {

  transform(questions: Question[]): Question[] {
    return questions.filter(question => question.closed === null);
  }

}
