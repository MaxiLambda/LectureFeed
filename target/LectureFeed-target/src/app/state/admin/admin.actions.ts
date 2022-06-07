import {createAction, props} from "@ngrx/store";
import {Token} from "../../model/token/token.model";
import {ISessionData} from "../../model/sessionCreateData/session-create-data.model";

export enum ActionDescription {
  SetCurrentDataSession = '[Session] set current session',
  RemoveCurrentDataSession = '[Session] remove current session',
}

export const setCurrentDataSession = createAction(
  ActionDescription.SetCurrentDataSession,
  props<{ sessionData: ISessionData }>(),
);

export const removeCurrentDataSession = createAction(
  ActionDescription.RemoveCurrentDataSession
);

