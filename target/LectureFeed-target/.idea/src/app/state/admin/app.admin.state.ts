import {ISessionData} from "../../model/sessionCreateData/session-create-data.model";
import {IAppTokenState} from "../token/app.token.state";

export interface IAppAdminState extends IAppTokenState{
  adminCurrentSessionData: ISessionData
}
