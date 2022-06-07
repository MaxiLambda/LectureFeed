import {Component, OnInit} from '@angular/core';
import {CustomRouterService} from "../../../service/customRouter/custom-router.service";

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.scss']
})
export class ContentComponent implements OnInit{
  minimalHeader: boolean = false;

  constructor(private customRouterService: CustomRouterService) {}

  ngOnInit(): void {
    this.minimalHeader = this.customRouterService.getObserverQueryParams().minimalheader as boolean;
  }
}
