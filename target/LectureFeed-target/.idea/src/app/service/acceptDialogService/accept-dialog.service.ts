import { Injectable } from '@angular/core';
import {AcceptDialogComponent} from "../../component/molecules/accept-dialog/accept-dialog.component";

@Injectable({
  providedIn: 'root'
})
export class AcceptDialogService {

  private dialogComponent: AcceptDialogComponent|null = null;

  registerDialog(dialogComponent: AcceptDialogComponent) {
    this.dialogComponent = dialogComponent;
    this.dialogComponent.onClose.subscribe(() => {
      if(this.currentResolve !== null){
        this.currentResolve();
        this.currentResolve = null;
      }
    })
  }

  private currentResolve: Function|null = null

  open(title: string, msg: string) {
    return new Promise((resolve: Function) =>{
      if(this.dialogComponent != null){
        this.dialogComponent.title = title;
        this.dialogComponent.msg = msg;
        this.dialogComponent.visible = true;
        this.currentResolve = resolve;
      }
    });
  }


}
