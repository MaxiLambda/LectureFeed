import {createSelector} from "@ngrx/store";
import {IAppParticipantState, IVotedQuestion} from "./app.participant.state";
import jwt_decode from "jwt-decode";

export const selectParticipantData = createSelector(
  (state: IAppParticipantState) => {
    return state.token;
  },
  (token: string) => {
    let payload = jwt_decode(token) as any;
    if(payload.hasOwnProperty("id") && payload.hasOwnProperty("username")){
      return {id: payload.id, nickname: payload.username};
    }
    return null
  },
);

export const selectQuestionIds = createSelector(
  (state: IAppParticipantState) => state.participantQuestionIds,
  (questionIds: number[]) => questionIds,
);

export const selectVotedQuestions = createSelector(
  (state: IAppParticipantState) => state.participantVotedQuestions,
  (votedQuestions: IVotedQuestion[]) => votedQuestions,
);
