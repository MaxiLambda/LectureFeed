import { Injectable } from '@angular/core';
import {DefaultService} from "../defaultService/default.service";
import {firstValueFrom, map} from "rxjs";
import {EnvironmentInfo} from "../../model/environmentInfo/environment-info.model";

@Injectable({
  providedIn: 'root'
})
export class EnvironmentService extends DefaultService{

  getEnvironmentInfo(){
    return firstValueFrom(this.http.get<EnvironmentInfo>(this.getAPIUrl()+`environment/info`)
      .pipe(
        map((v: EnvironmentInfo) => new EnvironmentInfo(v.routingIpInterface)
      )));
  }

}
