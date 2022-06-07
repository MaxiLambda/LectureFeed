import {createReducer, on} from "@ngrx/store";
import {removeToken, setToken} from "./token.actions";
import {CookieStore, StoreIds} from "../../lib/CookieStore";
import jwt_decode from "jwt-decode";
import {CookieAttributes} from "js-cookie";


export const initialTokenState: string = CookieStore.getOrDefault(StoreIds.token, "");

export const tokenReducer = createReducer(
  initialTokenState,
  on(setToken, setTokenReducer),
  on(removeToken, setRemoveTokenReducer)
);

function setTokenReducer(state: string, { token }: { token: string }): string {
  let options: CookieAttributes = {}
  if(token.length > 0){
    let payload = jwt_decode(token) as any;
    if(payload.hasOwnProperty("expirationDate")){
      options.expires = new Date(parseInt(payload?.expirationDate));
    }
  }
  CookieStore.set(StoreIds.token, token, options);
  return token;
}

function setRemoveTokenReducer(state: string): string {
  return setTokenReducer(state, {token: ""});
}


