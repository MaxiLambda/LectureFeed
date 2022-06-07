import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PresenterQuestionTableComponent } from './presenter-question-table.component';

describe('PresenterQuestionTableComponent', () => {
  let component: PresenterQuestionTableComponent;
  let fixture: ComponentFixture<PresenterQuestionTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PresenterQuestionTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PresenterQuestionTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
