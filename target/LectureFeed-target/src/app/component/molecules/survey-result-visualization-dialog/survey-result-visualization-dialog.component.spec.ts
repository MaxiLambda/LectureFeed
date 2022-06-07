import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SurveyResultVisualizationDialogComponent } from './survey-result-visualization-dialog.component';

describe('SurveyResultVisualizationDialogComponent', () => {
  let component: SurveyResultVisualizationDialogComponent;
  let fixture: ComponentFixture<SurveyResultVisualizationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SurveyResultVisualizationDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SurveyResultVisualizationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
