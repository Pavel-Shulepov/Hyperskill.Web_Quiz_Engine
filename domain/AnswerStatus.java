package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class AnswerStatus {
    private final boolean success;
    private final String feedback;

    @JsonCreator
    public AnswerStatus(@JsonProperty(value = "success") boolean success,
                        @JsonProperty(value = "feedback") String feedback) {
        this.success = success;
        this.feedback = feedback;
    }
}
