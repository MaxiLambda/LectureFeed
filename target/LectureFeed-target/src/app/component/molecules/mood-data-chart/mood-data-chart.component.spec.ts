import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MoodDataChartComponent } from './mood-data-chart.component';

describe('MoodDataChartComponent', () => {
  let component: MoodDataChartComponent;
  let fixture: ComponentFixture<MoodDataChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MoodDataChartComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MoodDataChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
