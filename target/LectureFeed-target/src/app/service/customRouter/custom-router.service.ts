import {Injectable, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationExtras, Router} from "@angular/router";

export interface IObserverQueryParams{
  minimalheader: null|boolean,
  hidefooter: null|boolean
}

@Injectable({
  providedIn: 'root'
})
export class CustomRouterService{

  observerQueryParams: IObserverQueryParams = {
    minimalheader: null,
    hidefooter: null
  }

  constructor(
    private readonly router: Router,
    private route: ActivatedRoute
  ) {
    this.route.queryParams.subscribe(value => {
      if(value.hasOwnProperty("minimalheader")) this.observerQueryParams.minimalheader = value.minimalheader == 1;
      if(value.hasOwnProperty("hidefooter")) this.observerQueryParams.hidefooter = value.hidefooter == 1;
    });
  }

  public getObserverQueryParams(): IObserverQueryParams
  {
    return this.fromEntries(Object.entries(this.observerQueryParams).map(([key, value]) => [key, value === null? false: value])) as IObserverQueryParams;
  }

  public fromEntries(xs: [string|number|symbol, any][]){
    return xs.reduce((acc, [key, value]) => ({...acc, [key]: value}), {});
  }


  private getFilteredObserverQueryParams(){
    return this.fromEntries(Object.entries(this.observerQueryParams)
      .filter(([key, value]) => value !== null)
      .map(([key, value])  => [key, (typeof value === "boolean"? (value? 1: 0): value)]));
  }

  public navigateWithObserverQueryParams(commands: any[], extras: NavigationExtras = {}){
    let mergedExtras: any = {};
    Object.assign(mergedExtras, { queryParams: this.getFilteredObserverQueryParams() }, extras);
    this.router.navigate(commands, mergedExtras);
  }

}
