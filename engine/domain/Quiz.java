package engine.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Quiz {
    private int id;

    private String title;

    private String text;

    private String[] options;
    @JsonIgnore
    private int[] answer;

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return options;
    }

    public int getId() {
        return id;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("options")
    public void setOptions(String[] options) {
        this.options = options;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    public int[] getAnswer() {
        return answer;
    }

    @JsonProperty("answer")
    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    public Quiz(String title,
                String text,
                String[] options,
                int[] answer) {
        this.answer = answer == null ? new int[]{} : answer;
        this.title = title;
        this.text = text;
        this.options = options;
    }
}
