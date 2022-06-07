import {Component, EventEmitter, OnInit, Output} from '@angular/core';

export interface IQuestionMessageTemplate{
  message: string;
  anonymous: boolean;
}

@Component({
  selector: 'app-create-question',
  templateUrl: './create-question.component.html',
  styleUrls: ['./create-question.component.scss']
})
export class CreateQuestionComponent implements OnInit {

  @Output() onCreatedQuestion: EventEmitter<IQuestionMessageTemplate> = new EventEmitter();
  message: string = "";
  anonymous: string[] = [];

  constructor() { }

  ngOnInit(): void {
  }

  onClickSend() {
    let anonymous = this.anonymous.length>0;
    this.onCreatedQuestion.emit({message: this.message, anonymous});
    this.message = "";
  }
}
