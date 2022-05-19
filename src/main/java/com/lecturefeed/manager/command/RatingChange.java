package com.lecturefeed.manager.command;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RatingChange {
    final int sessionId;
    final int questionId;
    final int voterId;
    final boolean ratingChange;
}
