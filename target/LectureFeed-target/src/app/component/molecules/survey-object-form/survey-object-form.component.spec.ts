import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SurveyObjectFormComponent } from './survey-object-form.component';

describe('SurveyObjectFormComponent', () => {
  let component: SurveyObjectFormComponent;
  let fixture: ComponentFixture<SurveyObjectFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SurveyObjectFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SurveyObjectFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
