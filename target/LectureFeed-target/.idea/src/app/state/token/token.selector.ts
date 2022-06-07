import {createSelector} from "@ngrx/store";
import {IAppTokenState} from "./app.token.state";

export const selectToken = createSelector(
  (state: IAppTokenState) => state.token,
  (token: string) => token,
);

