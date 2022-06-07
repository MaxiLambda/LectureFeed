import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from '@angular/core';
import {EnvironmentService} from "../../../service/environmentService/environment.service";
import {EnvironmentInfo} from "../../../model/environmentInfo/environment-info.model";
import { Options } from 'ngx-qrcode-styling';

@Component({
  selector: 'app-share-session-code-dialog',
  templateUrl: './share-session-code-dialog.component.html',
  styleUrls: ['./share-session-code-dialog.component.scss']
})
export class ShareSessionCodeDialogComponent implements OnChanges{

  @Input() sessionCode: string = "";
  @Input() sessionId: number|null = 0;
  @Input() visible: boolean = false;
  @Output() visibleChange: EventEmitter<boolean> = new EventEmitter();
  @Output() onHide: EventEmitter<void> = new EventEmitter();
  environmentInfo: EnvironmentInfo|null = null;

  public config: Options = {
    "margin":0,
    "qrOptions":{"typeNumber":0,"mode":"Byte","errorCorrectionLevel":"L"},
    "imageOptions":{"hideBackgroundDots":true,"imageSize":0.9,"margin":0},
    "dotsOptions":{"type":"rounded","color":"#1a6b41","gradient":{"type":"radial","rotation":0,"colorStops":[{"offset":0,"color":"#009688"},{"offset":1,"color":"#004942"}]}},
    "backgroundOptions":{"color":"#fff0","gradient":undefined},
    "cornersSquareOptions":{"type":"extra-rounded","color":"#006a61","gradient":undefined},
    "cornersDotOptions":{"type":undefined,"color":"#1b4f46"},
}

  constructor(
    private readonly environmentService: EnvironmentService) {
    this.environmentService.getEnvironmentInfo().then(info => {
      this.environmentInfo = info;
    })
  }

  ngOnChanges(changes: SimpleChanges) {
    if(changes.hasOwnProperty("visible")){
      this.visible = changes.visible.currentValue;
    }
  }

  getShareLink(){
    return `${location.protocol}//${this.environmentInfo?.routingIpInterface}:${location.port}/#/participant/join/${this.sessionId}`
  }

  onHideDialog() {
    this.visible = false;
    this.visibleChange.emit(this.visible);
    this.onHide.emit();
  }
}
