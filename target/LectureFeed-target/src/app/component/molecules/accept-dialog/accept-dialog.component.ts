import {Component, EventEmitter, Input, Output} from '@angular/core';
import {AcceptDialogService} from "../../../service/acceptDialogService/accept-dialog.service";

@Component({
  selector: 'app-accept-dialog',
  templateUrl: './accept-dialog.component.html',
  styleUrls: ['./accept-dialog.component.scss']
})
export class AcceptDialogComponent {

  visible: boolean = false;
  title: string = "";
  msg: string = "";
  @Output() onClose: EventEmitter<Date> = new EventEmitter();

  constructor(private readonly acceptDialogService: AcceptDialogService) {
    acceptDialogService.registerDialog(this);
  }

  onHide() {
    this.visible = false;
    this.onClose.emit(new Date());
  }
}
