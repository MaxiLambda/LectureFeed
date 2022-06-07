import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Participant} from "../../../model/participant/participant.model";

@Component({
  selector: 'app-participant-connection-table',
  templateUrl: './participant-connection-table.component.html',
  styleUrls: ['./participant-connection-table.component.scss']
})
export class ParticipantConnectionTableComponent {

  @Input() participants: Participant[] = [];
  @Output() onSelectParticipant: EventEmitter<Participant> = new EventEmitter();

  selectedParticipant(participant: Participant) {
    this.onSelectParticipant.emit(participant);
  }
}
