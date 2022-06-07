import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SurveyAnswerEventVisualizationComponent } from './survey-answer-event-visualization.component';

describe('SurveyAnswerEventVisualizationComponent', () => {
  let component: SurveyAnswerEventVisualizationComponent;
  let fixture: ComponentFixture<SurveyAnswerEventVisualizationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SurveyAnswerEventVisualizationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SurveyAnswerEventVisualizationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
