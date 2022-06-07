import { ComponentFixture, TestBed } from '@angular/core/testing';
import {MessageService} from "primeng/api";
import { ParticipantLoginComponent } from './participant-login.component';
import {RouterTestingModule} from "@angular/router/testing";
import {StoreModule} from "@ngrx/store";
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('ParticipantLoginComponent', () => {
  let component: ParticipantLoginComponent;
  let fixture: ComponentFixture<ParticipantLoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule, StoreModule.forRoot({})],
      providers:[MessageService],
      declarations: [ParticipantLoginComponent]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ParticipantLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create an instance', () => {
    expect(component).toBeTruthy();
  });
});
