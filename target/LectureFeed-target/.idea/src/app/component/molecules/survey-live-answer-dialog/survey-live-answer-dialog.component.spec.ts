import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SurveyLiveAnswerDialogComponent } from './survey-live-answer-dialog.component';

describe('SurveyLiveAnswerDialogComponent', () => {
  let component: SurveyLiveAnswerDialogComponent;
  let fixture: ComponentFixture<SurveyLiveAnswerDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SurveyLiveAnswerDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SurveyLiveAnswerDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
