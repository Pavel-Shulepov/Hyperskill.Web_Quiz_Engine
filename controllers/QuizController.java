package controllers;

import domain.AnswerStatus;
import domain.Quiz;
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

    @RequestMapping(method = RequestMethod.POST, path = "/quiz/{answer}")
    public AnswerStatus answerQuiz(@PathVariable int answer) {
        if (answer == 2) {
            return new AnswerStatus(true, "Congratulations, you're right!");
        } else {
            return new AnswerStatus(false, "Wrong answer! Please, try again.");
        }
    }

}
