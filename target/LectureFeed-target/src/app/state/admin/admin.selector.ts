import {createSelector} from "@ngrx/store";
import {IAppAdminState} from "./app.admin.state";
import {ISessionData} from "../../model/sessionCreateData/session-create-data.model";

export const selectCurrentSessionData = createSelector(
  (state: IAppAdminState) => {
    return state.adminCurrentSessionData;
  },
  (sessionData: ISessionData) => sessionData
);
