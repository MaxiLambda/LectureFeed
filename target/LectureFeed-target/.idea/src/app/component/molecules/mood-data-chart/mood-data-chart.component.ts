import {Component, Input, OnInit} from '@angular/core';
import {EChartsOption} from "echarts";
import {MoodEntity} from "../../../model/moodEntitiy/mood-entity.model";
import moment from "moment";
import {ColorUtils} from "../../../lib/ColorUtils";

@Component({
  selector: 'app-mood-data-chart',
  templateUrl: './mood-data-chart.component.html',
  styleUrls: ['./mood-data-chart.component.scss']
})
export class MoodDataChartComponent implements OnInit {

  @Input() displayLineLabel: string = "";
  @Input() lineHexColor: string = "#14B8A6";
  @Input() moodEntities: MoodEntity[] = [];

  options: any = {
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: []
    },
    legend: {
      data: []
    },
    yAxis: {
      type: 'value',
      min: -5,
      max: 5,
      interval: 1
    },
    tooltip: {
      trigger: 'axis',
      className: 'tooltip',
      formatter: (params: any) => {
        params = params[0];
        return params.axisValueLabel+ ' : ' + params.data;
      },
      axisPointer: {
        animation: false
      }
    },
    series: []
  }

  ngOnInit(): void {
    this.updateOptions();
  }

  private updateOptions(){
    let data: number[] = []
    for (const entity of this.moodEntities) {
      this.options.xAxis.data.push(moment(entity.timestamp).format("hh:mm:ss"));
      data.push(entity.value);
    }
    this.options.series.push({
      name: this.displayLineLabel,
      type: 'line',
      showSymbol: false,
      hoverAnimation: false,
      data: data,
      color: this.lineHexColor
    });
    this.options.legend.data.push(this.displayLineLabel)
  }

}
