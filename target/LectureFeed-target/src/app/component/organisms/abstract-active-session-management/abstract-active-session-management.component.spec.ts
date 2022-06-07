import {ComponentFixture, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {MessageService} from "primeng/api";
import {ParticipantSessionComponent} from "../../pages/participant/participant-session/paricipant-session.component";
import {AbstractActiveSessionManagementComponent} from "./abstract-active-session-management.component";

describe('AbstractActiveSessionManagementComponent', () => {
  let component: AbstractActiveSessionManagementComponent;
  let fixture: ComponentFixture<AbstractActiveSessionManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule],
      providers:[MessageService],
      declarations: [ParticipantSessionComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AbstractActiveSessionManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create an instance', () => {
    expect(component).toBeTruthy();
  });
});
