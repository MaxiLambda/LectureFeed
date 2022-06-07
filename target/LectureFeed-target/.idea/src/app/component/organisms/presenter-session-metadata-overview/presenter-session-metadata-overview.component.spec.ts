import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PresenterSessionMetadataOverviewComponent } from './presenter-session-metadata-overview.component';

describe('PresenterSessionMetadataOverviewComponent', () => {
  let component: PresenterSessionMetadataOverviewComponent;
  let fixture: ComponentFixture<PresenterSessionMetadataOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PresenterSessionMetadataOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PresenterSessionMetadataOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
