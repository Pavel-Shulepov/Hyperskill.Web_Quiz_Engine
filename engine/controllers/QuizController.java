package engine.controllers;

import engine.domain.AnswerStatus;
import engine.domain.Quiz;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class QuizController {

    @RequestMapping(method = RequestMethod.GET, path = "/quiz")
    public Quiz getQuiz() {
        return new Quiz("The Java Logo",
                "What is depicted on the Java logo?",
                new String[] {"Robot","Tea leaf","Cup of coffee","Bug"},
                2);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/quiz")
    public AnswerStatus answerQuiz(@RequestParam("answer") int answer) {
        if (answer == 2) {
            return new AnswerStatus(true, "Congratulations, you're right!");
        } else {
            return new AnswerStatus(false, "Wrong answer! Please, try again.");
        }
    }

}
