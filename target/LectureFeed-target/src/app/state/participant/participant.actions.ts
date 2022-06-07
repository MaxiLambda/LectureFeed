import {createAction, props} from "@ngrx/store";
import {Token} from "../../model/token/token.model";
import {IVotedQuestion} from "./app.participant.state";

export enum ActionDescription {
  //SetParticipantData = '[Session] set participant data',
  SetQuestionIds = '[Session] set question ids',
  SetVotedQuestions = '[Session] set voted questions',
  deleteVotedQuestions = '[Session] delete voted questions',
}



export const setQuestionIds = createAction(
  ActionDescription.SetQuestionIds,
  props<{ questionIds: number[] }>(),
);

export const pushQuestionId = createAction(
  ActionDescription.SetQuestionIds,
  props<{ questionId: number }>(),
);

export const votedQuestion = createAction(
  ActionDescription.SetVotedQuestions,
  props<{ votedQuestion: IVotedQuestion }>(),
)

export const deleteVotedQuestion = createAction(
  ActionDescription.deleteVotedQuestions
)
