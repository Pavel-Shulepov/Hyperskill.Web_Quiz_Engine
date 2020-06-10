package engine.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import engine.domain.Option;
import engine.domain.Quiz;

import java.io.IOException;

public class QuizSerializer extends JsonSerializer<Quiz> {

    @Override
    public void serialize(Quiz value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("title", value.getTitle());
        gen.writeStringField("text", value.getText());
        gen.writeNumberField("id", value.getId());

        gen.writeArrayFieldStart("options");
        for (Option option: value.getOptions()) {
            gen.writeString(option.getText());
        }
        gen.writeEndArray();

        gen.writeEndObject();
    }
}
