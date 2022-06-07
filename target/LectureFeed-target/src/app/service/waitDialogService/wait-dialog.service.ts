import {Injectable} from '@angular/core';
import {WaitDialogComponent} from "../../component/molecules/wait-dialog/wait-dialog.component";

@Injectable({
  providedIn: 'root'
})
export class WaitDialogService {

  private dialogComponent: WaitDialogComponent|null = null;

  registerDialog(dialogComponent: WaitDialogComponent) {
    this.dialogComponent = dialogComponent;
  }

  open(msg: string) {
    if(this.dialogComponent != null){
      this.dialogComponent.msg = msg;
      this.dialogComponent.visible = true;
    }
  }

  close() {
    if(this.dialogComponent != null){
      this.dialogComponent.visible = false;
    }
  }

}
