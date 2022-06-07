import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SurveySelectorDialogComponent } from './survey-selector-dialog.component';

describe('SurveySelectorDialogComponent', () => {
  let component: SurveySelectorDialogComponent;
  let fixture: ComponentFixture<SurveySelectorDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SurveySelectorDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SurveySelectorDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
