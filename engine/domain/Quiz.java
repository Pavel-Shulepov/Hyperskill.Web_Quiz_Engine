package engine.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Quiz {
    private int id;
    @JsonProperty("title")
    public final String title;
    @JsonProperty("text")
    public final String text;
    @JsonProperty("options")
    public final String[] options;
    @JsonIgnore
    public int answer;

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


    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    public int getAnswer() {
        return answer;
    }

    @JsonProperty("answer")
    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public Quiz(String title,
                String text,
                String[] options,
                int answer) {
        this.answer = answer;
        this.title = title;
        this.text = text;
        this.options = options;
    }
}
