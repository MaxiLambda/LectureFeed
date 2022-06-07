import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Survey} from "../../../model/survey/survey.model";
import {SurveyType} from "../../../model/surveyTemplate/survey-template.model";
import {EChartsOption} from "echarts";

@Component({
  selector: 'app-survey-answer-visualization',
  templateUrl: './survey-answer-visualization.component.html',
  styleUrls: ['./survey-answer-visualization.component.scss']
})
export class SurveyAnswerVisualizationComponent implements OnInit, OnChanges{

  @Input() survey: Survey|null = null
  options: EChartsOption = {};

  private isType(needed: SurveyType){
    return this.survey?.template.type === needed;
  }

  isYesNoType(){
    return this.isType(SurveyType.YesNo);
  }

  isRatingType(){
    return this.isType(SurveyType.Rating);
  }

  isOpenAnswerType(){
    return this.isType(SurveyType.OpenAnswer);
  }

  ngOnInit(): void {
    this.updateSurveyOptions(this.survey);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if(changes.hasOwnProperty("survey")){
      this.survey = changes.survey.currentValue;
      this.updateSurveyOptions(this.survey);
    }
  }

  private updateSurveyOptions(survey: Survey|null){
    if(this.isRatingType() && this.survey !== null){
      this.options = this.createRatingOptionsBySurvey(this.survey)
    }else if(this.isYesNoType() && this.survey !== null){
      this.options = this.createYesNoOptionsBySurvey(this.survey)
    }
  }

  createRatingOptionsBySurvey(survey: Survey): EChartsOption
  {
    let serie: any = {
      name: 'area',
      type: 'pie',
      radius: [30, 110],
      roseType: 'area',
      data: []
    };
    let values: number[] = survey.answers.map(s => parseInt(s));
    const minValue = Math.min.apply(Math, values);
    const maxValue = Math.max.apply(Math, values)+1;
    for (let i = minValue; i < maxValue; i++) {
      let count = values.filter(v => v === i).length;
      if(count > 0){
        serie.data.push({ value: count, name: String(i) })
      }
    }
    return {
      tooltip: {
        trigger: 'item',
        formatter: 'Value {b} <br/> {c} ({d}%)'
      },
      calculable: true,
      series: [serie]
    };
  }


  private createYesNoOptionsBySurvey(survey: Survey): EChartsOption
  {
    let serie: any = {
      name: 'area',
      type: 'pie',
      radius: [30, 110],
      roseType: 'area',
      data: []
    };
    let values: number[] = survey.answers.map(s => parseInt(s));
    serie.data.push({ value: values.filter(v => v===1).length, name: "Yes" });
    serie.data.push({ value: values.filter(v => v===-1).length, name: "No" });
    return {
      tooltip: {
        trigger: 'item',
        formatter: '{a} {b} <br/> {c} ({d}%)'
      },
      calculable: true,
      series: [serie]
    };
  }
}
