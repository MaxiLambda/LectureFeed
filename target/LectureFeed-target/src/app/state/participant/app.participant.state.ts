import {IAppTokenState} from "../token/app.token.state";

export interface IVotedQuestion{
  questionId: number
  vote: boolean
}

export interface IAppParticipantState extends IAppTokenState{
  participantQuestionIds: number[]
  participantVotedQuestions: IVotedQuestion[]
}
