import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParticipantConnectionTableComponent } from './participant-connection-table.component';

describe('ParticipantConnectionTableComponent', () => {
  let component: ParticipantConnectionTableComponent;
  let fixture: ComponentFixture<ParticipantConnectionTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ParticipantConnectionTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ParticipantConnectionTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
