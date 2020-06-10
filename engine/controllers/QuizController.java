package engine.controllers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import engine.domain.Answer;
import engine.domain.AnswerStatus;
import engine.domain.Quiz;
import engine.json.QuizBuilderDeserializer;
import engine.services.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequestMapping("/api")
@Validated
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/quizzes/{id}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable("id") int id) {
        Quiz quiz = quizService.get(id);
        if (quiz != null) {
            return ResponseEntity.ok(quiz);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @RequestMapping(method = RequestMethod.POST, path = "/quizzes/{id}/solve")
    public ResponseEntity<AnswerStatus> answerQuiz(@RequestBody Answer answer,
                                                   @PathVariable("id") int id) {
        if (quizService.solve(id, answer)) {
            return ResponseEntity.ok(new AnswerStatus(true, "Congratulations, you're right!"));
        } else {
            return ResponseEntity.ok(new AnswerStatus(false, "Wrong answer! Please, try again."));
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/quizzes")
    public ResponseEntity<Quiz> addQuiz(@JsonDeserialize(using = QuizBuilderDeserializer.class) @RequestBody Quiz.Builder quizBuilder) {
        Quiz quiz = quizBuilder.build();
        if (quiz.getOptions() == null || quiz.getOptions().size() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверное количество ответов");
        }
        return ResponseEntity.ok(quizService.add(quiz));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/quizzes")
    public ResponseEntity<Collection<Quiz>> addQuiz() {
        return ResponseEntity.ok(quizService.all());
    }

}
