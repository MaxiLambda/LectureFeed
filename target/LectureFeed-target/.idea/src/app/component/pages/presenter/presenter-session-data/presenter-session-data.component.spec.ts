import {ComponentFixture, TestBed} from '@angular/core/testing';
import {PresenterSessionDataComponent} from './presenter-session-data.component';
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {StoreModule} from "@ngrx/store";
import {ConfirmationService, MessageService} from "primeng/api";
import {PresenterSessionComponent} from "../presenter-session/presenter-session.component";

describe('PresenterSessionDataComponent', () => {
  let component: PresenterSessionDataComponent;
  let fixture: ComponentFixture<PresenterSessionDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule, StoreModule.forRoot({})],
      providers:[MessageService, ConfirmationService],
      declarations: [PresenterSessionComponent]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PresenterSessionDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});


