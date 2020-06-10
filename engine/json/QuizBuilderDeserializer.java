package engine.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import engine.domain.AnswerEntity;
import engine.domain.Option;
import engine.domain.Quiz;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.ArrayList;

@JsonComponent
public class QuizBuilderDeserializer extends JsonDeserializer<Quiz.Builder> {
    @Override
    public Quiz.Builder deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Quiz.Builder builder = new Quiz.Builder();
        for (JsonToken token = p.nextToken(); p.hasCurrentToken(); token = p.nextToken()) {
            if (!JsonToken.FIELD_NAME.equals(token)) {
                continue;
            }

            switch (p.getValueAsString()) {
                case "title":
                    builder.withTitle(p.nextTextValue());
                    break;
                case "text":
                    builder.withText(p.nextTextValue());
                    break;
                case "answer":
                    ArrayList<AnswerEntity> answers = new ArrayList<>();
                    token = p.nextToken();
                    while(!JsonToken.END_ARRAY.equals(token = p.nextToken())) {
                        answers.add(new AnswerEntity(p.getValueAsInt()));
                    }
                    builder.addAllAnswers(answers);
                    break;
                case "options":
                    ArrayList<Option> options = new ArrayList<>();
                    token = p.nextToken();
                    while(!JsonToken.END_ARRAY.equals(token = p.nextToken())) {
                        options.add(new Option(p.getValueAsString()));
                    }
                    builder.addAllOptions(options);
                    break;
            }
        }
        return builder;
    }
}
