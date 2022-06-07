import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PresenterCreateSessionDialogComponent } from './presenter-create-session-dialog.component';

describe('PresenterCreateSessionDialogComponent', () => {
  let component: PresenterCreateSessionDialogComponent;
  let fixture: ComponentFixture<PresenterCreateSessionDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PresenterCreateSessionDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PresenterCreateSessionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
