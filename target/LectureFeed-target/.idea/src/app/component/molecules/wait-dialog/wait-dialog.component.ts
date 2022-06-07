import {Component, Input} from '@angular/core';
import {WaitDialogService} from "../../../service/waitDialogService/wait-dialog.service";


@Component({
  selector: 'app-wait-dialog',
  templateUrl: './wait-dialog.component.html',
  styleUrls: ['./wait-dialog.component.scss']
})
export class WaitDialogComponent{

  visible: boolean = false;
  msg: string = "";

  constructor(private readonly waitDialogService: WaitDialogService) {
    waitDialogService.registerDialog(this);
  }

}
