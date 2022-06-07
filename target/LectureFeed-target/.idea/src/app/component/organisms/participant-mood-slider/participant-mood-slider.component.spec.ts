import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParticipantMoodSliderComponent } from './participant-mood-slider.component';

describe('ParticipantMoodSliderComponent', () => {
  let component: ParticipantMoodSliderComponent;
  let fixture: ComponentFixture<ParticipantMoodSliderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ParticipantMoodSliderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ParticipantMoodSliderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
