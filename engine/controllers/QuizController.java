package engine.controllers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import engine.domain.Answer;
import engine.domain.AnswerStatus;
import engine.domain.Complete;
import engine.domain.Quiz;
import engine.json.QuizBuilderDeserializer;
import engine.services.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class QuizController {

    private final QuizService quizService;
    private final Logger logger = LoggerFactory.getLogger(QuizController.class);

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
                                                   @PathVariable("id") int id,
                                                   HttpServletRequest request) {
        if (quizService.solve(id, answer, request.getUserPrincipal().getName())) {
            return ResponseEntity.ok(new AnswerStatus(true, "Congratulations, you're right!"));
        } else {
            return ResponseEntity.ok(new AnswerStatus(false, "Wrong answer! Please, try again."));
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/quizzes")
    public ResponseEntity<Quiz> addQuiz(@JsonDeserialize(using = QuizBuilderDeserializer.class) @RequestBody Quiz.Builder quizBuilder,
                                        HttpServletRequest request) {
        Quiz quiz = quizBuilder.build();
        quiz.setUserEmail(request.getUserPrincipal().getName());
        if (quiz.getOptions() == null || quiz.getOptions().size() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверное количество ответов");
        }
        logger.debug("Add quiz = {}", quiz);
        return ResponseEntity.ok(quizService.add(quiz));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/quizzes")
    public ResponseEntity<Page<Quiz>> allQuiz(@RequestParam(name = "page") int page) {
        logger.debug("{}", page);
        return ResponseEntity.ok(quizService.all(page));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/quizzes/completed")
    public ResponseEntity<Page<Complete>> allCompleteByUser(@RequestParam(name = "page") int page,
                                                            HttpServletRequest request) {
        logger.debug("{}", page);
        return ResponseEntity.ok(quizService.completes(request.getUserPrincipal().getName(), page));
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/quizzes/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable("id") int id,
                                        HttpServletRequest request) {
        if (!quizService.get(id).getUserEmail().equals(request.getUserPrincipal().getName())) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Только автор может удалить тест");
        quizService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
