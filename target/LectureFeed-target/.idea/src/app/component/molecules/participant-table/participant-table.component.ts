import {Component, Input} from '@angular/core';
import {Participant} from "../../../model/participant/participant.model";

@Component({
  selector: 'app-participant-table',
  templateUrl: './participant-table.component.html',
  styleUrls: ['./participant-table.component.scss']
})
export class ParticipantTableComponent {

  @Input() participants: Participant[] = [];

}
