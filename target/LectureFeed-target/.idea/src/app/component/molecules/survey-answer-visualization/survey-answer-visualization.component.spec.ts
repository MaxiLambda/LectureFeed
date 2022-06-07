import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SurveyAnswerVisualizationComponent } from './survey-answer-visualization.component';

describe('SurveyAnswerVisualizationComponent', () => {
  let component: SurveyAnswerVisualizationComponent;
  let fixture: ComponentFixture<SurveyAnswerVisualizationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SurveyAnswerVisualizationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SurveyAnswerVisualizationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
