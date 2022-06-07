import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ContentComponent} from './content.component';
import {MessageService} from "primeng/api";
import {ConfirmationService} from 'primeng/api';
import {RouterTestingModule} from "@angular/router/testing";

describe('ContentComponent', () => {
  let component: ContentComponent;
  let fixture: ComponentFixture<ContentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers:[MessageService, ConfirmationService],
      declarations: [ ContentComponent ],
      imports: [RouterTestingModule],
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create an instance', () => {
    expect(component).toBeTruthy();
  });
});
