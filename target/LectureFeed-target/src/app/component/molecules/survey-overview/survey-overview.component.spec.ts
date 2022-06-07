import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SurveyOverviewComponent } from './survey-overview.component';

describe('SurveyOverviewComponent', () => {
  let component: SurveyOverviewComponent;
  let fixture: ComponentFixture<SurveyOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SurveyOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SurveyOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
