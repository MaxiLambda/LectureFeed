import {Component, OnInit} from '@angular/core';
import {CustomRouterService} from "../../../service/customRouter/custom-router.service";

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {
  visible: boolean = true;

  constructor(private customRouterService: CustomRouterService) { }

  ngOnInit(): void {
    this.visible = !this.customRouterService.getObserverQueryParams().hidefooter as boolean;
  }

}
