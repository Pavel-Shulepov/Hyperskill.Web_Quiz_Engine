package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Quiz {
    private final String title;
    private final String text;
    private final String[] options;
    private final int answerIndex;

    @JsonCreator
    public Quiz(@JsonProperty(value = "title") String title,
                @JsonProperty(value = "text") String text,
                @JsonProperty(value = "options") String[] options,
                int answerIndex) {
        this.answerIndex = answerIndex;
        this.title = title;
        this.text = text;
        this.options = options;
    }
}
