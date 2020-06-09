package engine.controllers;

import engine.domain.AnswerStatus;
import engine.domain.Quiz;
import engine.services.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
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
    public AnswerStatus answerQuiz(@RequestParam("answer") int answer, @PathVariable("id") int id) {
        if (quizService.solve(id, answer)) {
            return new AnswerStatus(true, "Congratulations, you're right!");
        } else {
            return new AnswerStatus(false, "Wrong answer! Please, try again.");
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/quizzes")
    public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz) {
        return ResponseEntity.ok(quizService.add(quiz));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/quizzes")
    public ResponseEntity<Collection<Quiz>> addQuiz() {
        return ResponseEntity.ok(quizService.all());
    }

}
