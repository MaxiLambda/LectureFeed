import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import {CookieStore, StoreIds} from "./CookieStore";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let token = CookieStore.getOrDefault(StoreIds.token, null);

    if (token === null || token.length === 0) {
      return next.handle(req);
    }

    const req1 = req.clone({
      headers: req.headers.set('Authorization', token),
    });

    return next.handle(req1);
  }

}
