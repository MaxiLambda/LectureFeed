import {Component, OnInit} from '@angular/core';
import {EasterEgg} from "./lib/EasterEgg";
import {environment} from "../environments/environment";
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{

  constructor(private titleService:Title) {
    this.titleService.setTitle("LectureFeed");
  }

  ngOnInit(): void {
    if(environment.production){
      EasterEgg.print()
    }
  }


}
