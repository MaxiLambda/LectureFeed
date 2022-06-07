import { Pipe, PipeTransform } from '@angular/core';
import {SurveyType} from "../model/surveyTemplate/survey-template.model";

@Pipe({
  name: 'displaySurveyEnumName',
  pure: false
})
export class DisplaySurveyEnumNamePipe implements PipeTransform {

  transform(nr: number): string {
    return SurveyType[nr];
  }

}
