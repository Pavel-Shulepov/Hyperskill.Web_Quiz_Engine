package engine.services;

import engine.domain.Answer;
import engine.domain.AnswerEntity;
import engine.domain.Quiz;
import engine.perositories.AnswerRepo;
import engine.perositories.OptionRepo;
import engine.perositories.QuizRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class QuizService {

    private final QuizRepo quizRepo;
    private final OptionRepo optionRepo;
    private final AnswerRepo answerRepo;

    public QuizService(QuizRepo quizRepo, OptionRepo optionRepo, AnswerRepo answerRepo) {
        this.quizRepo = quizRepo;
        this.optionRepo = optionRepo;
        this.answerRepo = answerRepo;
    }

    public Quiz add(Quiz quiz) {
        quiz.getOptions().forEach(optionRepo::save);
        quiz.getAnswer().forEach(answerRepo::save);
        return quizRepo.save(quiz);
    }

    public Quiz get(int id) {
        return quizRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Викторина не найдена"));
    }

    public Collection<Quiz> all() {
        return quizRepo.findAll();
    }

    public boolean solve(int id, Answer answer) {
        Quiz quiz = get(id);
        int[] a = new int[quiz.getAnswer().size()];
        int indx = 0;
        for (AnswerEntity answerEntity : quiz.getAnswer()) a[indx++] = answerEntity.getAnswerIndex();
        int[] b = answer.getAnswer();
        Arrays.sort(a);
        Arrays.sort(b);
        return Arrays.equals(a, b);
    }

    public void delete(int id) {
        quizRepo.delete(get(id));
    }

}
