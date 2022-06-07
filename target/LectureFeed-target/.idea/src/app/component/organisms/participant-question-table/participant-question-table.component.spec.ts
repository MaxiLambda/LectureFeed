import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParticipantQuestionTableComponent } from './participant-question-table.component';

describe('ParticipantQuestionTableComponent', () => {
  let component: ParticipantQuestionTableComponent;
  let fixture: ComponentFixture<ParticipantQuestionTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ParticipantQuestionTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ParticipantQuestionTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
