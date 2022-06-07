import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SurveyLiveViewDialogComponent } from './survey-live-view-dialog.component';

describe('SurveyLiveViewDialogComponent', () => {
  let component: SurveyLiveViewDialogComponent;
  let fixture: ComponentFixture<SurveyLiveViewDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SurveyLiveViewDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SurveyLiveViewDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
