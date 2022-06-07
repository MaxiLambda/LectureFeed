import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {SessionMetadata} from "../../../model/sessionMetadata/session-metadata.model";
import {Question} from "../../../model/question/question.model";

@Component({
  selector: 'app-presenter-session-metadata-overview',
  templateUrl: './presenter-session-metadata-overview.component.html',
  styleUrls: ['./presenter-session-metadata-overview.component.scss']
})
export class PresenterSessionMetadataOverviewComponent{

  @Input() sessionsMetadata: SessionMetadata[] = [];
  selectedSessionMetadata: SessionMetadata|null = null;
  @Output() onSelectSessionMetadata: EventEmitter<SessionMetadata> = new EventEmitter();

  onRowSelect(){
    this.onSelectSessionMetadata.emit(this.selectedSessionMetadata as SessionMetadata);
  }

}
