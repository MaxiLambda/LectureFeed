import {createReducer, on} from "@ngrx/store";
import {removeCurrentDataSession, setCurrentDataSession} from "./admin.actions";
import {ISessionData} from "../../model/sessionCreateData/session-create-data.model";
import {CookieStore, StoreIds} from "../../lib/CookieStore";


export const defaultSessionData: ISessionData = {
  id: 0,
  sessionCode: ""
}
export const initialSessionData: ISessionData = CookieStore.getJSONObjectOrDefault(StoreIds.adminCurrentSessionData,  defaultSessionData);


export const adminCurrentSessionReducer = createReducer(
  initialSessionData,
  on(setCurrentDataSession, setCurrentSessionDataReducer),
  on(removeCurrentDataSession, removeCurrentDataSessionReducer)
);

function setCurrentSessionDataReducer(state: ISessionData, { sessionData }: { sessionData: ISessionData }): ISessionData {
  CookieStore.setObject(StoreIds.adminCurrentSessionData, {id: sessionData.id, sessionCode: sessionData.sessionCode});
  return sessionData;
}

function removeCurrentDataSessionReducer(state: ISessionData): ISessionData {
  return setCurrentSessionDataReducer(state, {sessionData: defaultSessionData})
}

