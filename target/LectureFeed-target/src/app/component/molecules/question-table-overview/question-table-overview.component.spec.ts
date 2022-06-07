import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuestionTableOverviewComponent } from './question-table-overview.component';

describe('QuestionTableOverviewComponent', () => {
  let component: QuestionTableOverviewComponent;
  let fixture: ComponentFixture<QuestionTableOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ QuestionTableOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(QuestionTableOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
