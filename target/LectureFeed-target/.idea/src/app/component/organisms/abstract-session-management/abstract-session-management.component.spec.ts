import {ComponentFixture, TestBed} from '@angular/core/testing';
import { AbstractSessionManagementComponent } from './abstract-session-management.component';
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {MessageService} from "primeng/api";
import {ParticipantSessionComponent} from "../../pages/participant/participant-session/paricipant-session.component";

describe('AbstractSessionManagementComponent', () => {
  let component: AbstractSessionManagementComponent;
  let fixture: ComponentFixture<AbstractSessionManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule],
      providers:[MessageService],
      declarations: [ParticipantSessionComponent]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AbstractSessionManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create an instance', () => {
    expect(component).toBeTruthy();
  });
});
