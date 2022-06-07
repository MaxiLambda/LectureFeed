import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ShareSessionCodeDialogComponent} from './share-session-code-dialog.component';
import {ConfirmationService, MessageService} from "primeng/api";
import {EnvironmentService} from "../../../service/environmentService/environment.service";
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {StoreModule} from "@ngrx/store";

describe('ShareSessionCodeDialogComponent', () => {
  let component: ShareSessionCodeDialogComponent;
  let fixture: ComponentFixture<ShareSessionCodeDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ShareSessionCodeDialogComponent],
      providers:[EnvironmentService]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShareSessionCodeDialogComponent);
    component = fixture.componentInstance;
    component.sessionId = 0;
    component.visible = true;
    component.sessionCode = "code";
    spyOn(component.onHide, 'emit');
    spyOn(component, 'getShareLink');
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
