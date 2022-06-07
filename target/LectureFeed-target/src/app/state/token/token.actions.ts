import {createAction, props} from "@ngrx/store";


export enum ActionDescription {
  SetToken = '[Token] set token',
  removeToken = '[Token] remove token',
}

export const setToken = createAction(
  ActionDescription.SetToken,
  props<{ token: string }>(),
);

export const removeToken = createAction(
  ActionDescription.removeToken
);


