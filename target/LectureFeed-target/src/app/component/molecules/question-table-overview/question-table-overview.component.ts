import {Component, ContentChildren, Directive, Input, OnInit, QueryList, TemplateRef} from '@angular/core';
import {Question} from "../../../model/question/question.model";
import {timer} from "rxjs";

export interface ICustomColumnHeader{
  title: string;
  field?: string;
  sort?: boolean;
}

@Component({
  selector: 'app-question-table-overview',
  templateUrl: './question-table-overview.component.html',
  styleUrls: ['./question-table-overview.component.scss']
})
export class QuestionTableOverviewComponent implements OnInit {

  @Input() questions: Question[] = [];
  @Input() customColumnHeaders: ICustomColumnHeader[] = [];
  @Input() customHeader: TemplateRef<any>| null = null;

  currentCustomHeader: TemplateRef<any> | null = null;

  ngOnInit(): void {
    this.currentCustomHeader = this.customHeader;
  }

}
