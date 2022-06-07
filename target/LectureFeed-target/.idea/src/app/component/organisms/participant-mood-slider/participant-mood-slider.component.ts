import {Component, EventEmitter, OnDestroy, Output} from '@angular/core';
import {Options} from '@angular-slider/ngx-slider';

@Component({
  selector: 'app-participant-mood-slider',
  templateUrl: './participant-mood-slider.component.html',
  styleUrls: ['./participant-mood-slider.component.scss']
})
export class ParticipantMoodSliderComponent implements OnDestroy{

  private timeout: NodeJS.Timeout = setTimeout(()=>{}, 0);
  @Output() onChange: EventEmitter<number> = new EventEmitter();
  @Output() onUserChange: EventEmitter<number> = new EventEmitter();

  value: number = 0;
  options: Options = {
    floor: -5,
    ceil: 5,
    vertical: true
  };

  ngOnDestroy(): void {
    clearTimeout(this.timeout);
  }

  private startInterval(){
    clearTimeout(this.timeout);
    if(this.value > 0) this.value -= 1;
    if(this.value < 0) this.value += 1;
    if(this.value !== 0) {
      this.timeout = setTimeout(() => this.startInterval(), 1000);
    }
    this.onChange.emit(this.value);
  }

  userChangeEnd() {
    this.onUserChange.emit(this.value);
    this.onChange.emit(this.value);
    clearTimeout(this.timeout);
    this.timeout = setTimeout(() => this.startInterval(), 1000);
  }
}
