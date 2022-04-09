import {Component, OnInit} from '@angular/core';
import {EasterEgg} from "./lib/EasterEgg";
import {environment} from "../environments/environment";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{

  ngOnInit(): void {
    if(environment.production){
      EasterEgg.print()
    }
  }


}
