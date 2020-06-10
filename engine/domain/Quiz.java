package engine.domain;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import engine.json.QuizSerializer;

import javax.persistence.*;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Entity
@Table(name = "quiz")
@JsonSerialize(using = QuizSerializer.class)
public class Quiz {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @OneToMany()
    private List<Option> options;

    @OneToMany()
    @JoinColumn(name = "id_quiz")
    private List<AnswerEntity> answer;

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public List<Option> getOptions() {
        return options;
    }

    public Integer getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<AnswerEntity> getAnswer() {
        return answer;
    }

    public void setAnswer(List<AnswerEntity> answer) {
        this.answer = answer;
    }

    public Quiz() {
    }

    public Quiz(String title,
                String text,
                List<Option> options,
                List<AnswerEntity> answer) {
        this.answer = answer == null ? new ArrayList<>() : answer;
        this.title = title;
        this.text = text;
        this.options = options;
    }

    public static class Builder {

        private Map<String, Consumer<Quiz>> setters = new HashMap<>();

        private String title;
        private List<Option> options;
        private String text;
        private List<AnswerEntity> answer;

        public Builder() {
        }

        public List<Option> getOptions() {
            if (options == null) {
                options = new ArrayList<>();
            }
            return options;
        }

        public List<AnswerEntity> getAnswer() {
            if (answer == null) {
                answer = new ArrayList<>();
            }
            return answer;
        }

        public Builder withTitle(String title) {
            return withProperty("title", Quiz::setTitle, title);
        }

        public Builder withText(String text) {
            return withProperty("text", Quiz::setText, text);
        }

        public Builder addOptions(Option... options) {
            return this.addAllOptions(Arrays.asList(options));
        }

        public Builder addAllOptions(Collection<? extends Option> options) {
            this.getOptions().addAll(options);
            return this;
        }

        public Builder addAnswers(AnswerEntity... answers) {
            return this.addAllAnswers(Arrays.asList(answers));
        }

        public Builder addAllAnswers(Collection<? extends AnswerEntity> answers) {
            this.getAnswer().addAll(answers);
            return this;
        }

        private <T> Builder withProperty(String propertyName, BiConsumer<Quiz, T> function, T value) {
            setters.put(propertyName, task -> function.accept(task, value));
            return this;
        }

        public Quiz build() {
            Quiz quiz = new Quiz(title, text, options, answer);
            return apply(quiz);
        }

        private Quiz apply(Quiz quiz) {
            setters.values().forEach(consumer -> consumer.accept(quiz));
            return quiz;
        }

    }
}


