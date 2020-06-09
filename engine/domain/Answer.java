package engine.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Answer {

    private int[] answer;

    public int[] getAnswer() {
        return answer;
    }

    @JsonProperty("answer")
    public void setAnswer(int[] answer) {
        this.answer = answer;
    }
}
