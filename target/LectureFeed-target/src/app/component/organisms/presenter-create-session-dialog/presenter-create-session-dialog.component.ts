import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from '@angular/core';

@Component({
  selector: 'app-presenter-create-session-dialog',
  templateUrl: './presenter-create-session-dialog.component.html',
  styleUrls: ['./presenter-create-session-dialog.component.scss']
})
export class PresenterCreateSessionDialogComponent implements OnChanges{

  @Input() visible: boolean = true;
  @Output() visibleChange: EventEmitter<boolean> = new EventEmitter();
  @Output() onHide: EventEmitter<string> = new EventEmitter();
  name: string = "";

  ngOnChanges(changes: SimpleChanges) {
    if(changes.hasOwnProperty("visible")){
      this.visible = changes.visible.currentValue;
    }
  }

  onHideDialog() {
    this.visible = false;
    this.visibleChange.emit(this.visible);
    this.onHide.emit("");
    this.name = "";
  }

  onCreate() {
    this.visible = false;
    this.visibleChange.emit(this.visible);
    this.onHide.emit(this.name);
    this.name = "";
  }
}
