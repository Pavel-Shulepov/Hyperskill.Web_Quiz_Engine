package engine.services;

import engine.domain.Answer;
import engine.domain.AnswerEntity;
import engine.domain.Complete;
import engine.domain.Quiz;
import engine.perositories.AnswerRepo;
import engine.perositories.CompleteRepo;
import engine.perositories.OptionRepo;
import engine.perositories.QuizRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.*;

@Service
public class QuizService {

    private final QuizRepo quizRepo;
    private final OptionRepo optionRepo;
    private final AnswerRepo answerRepo;
    private final CompleteRepo completeRepo;

    public QuizService(QuizRepo quizRepo, OptionRepo optionRepo, AnswerRepo answerRepo, CompleteRepo completeRepo) {
        this.quizRepo = quizRepo;
        this.optionRepo = optionRepo;
        this.answerRepo = answerRepo;
        this.completeRepo = completeRepo;
    }

    public Quiz add(Quiz quiz) {
        quiz.getOptions().forEach(optionRepo::save);
        quiz.getAnswer().forEach(answerRepo::save);
        return quizRepo.save(quiz);
    }

    public Quiz get(int id) {
        return quizRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Викторина не найдена"));
    }

    public Page<Quiz> all(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10);
        return quizRepo.findAll(pageable);
    }

    public boolean solve(int id, Answer answer, String userEmail) {
        Quiz quiz = get(id);
        int[] a = new int[quiz.getAnswer().size()];
        int indx = 0;
        for (AnswerEntity answerEntity : quiz.getAnswer()) a[indx++] = answerEntity.getAnswerIndex();
        int[] b = answer.getAnswer();
        Arrays.sort(a);
        Arrays.sort(b);
        if (Arrays.equals(a, b)) {
            Complete complete = new Complete();
            complete.setCompletedAt(ZonedDateTime.now());
            complete.setQuizId(quiz.getId());
            complete.setUserEmail(userEmail);
            completeRepo.save(complete);
            return true;
        } return false;
    }

    public Page<Complete> completes(String userEmail, Integer page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("completedAt").descending());
        return completeRepo.findAllByUserEmail(pageable, userEmail);
    }

    public void delete(int id) {
        quizRepo.delete(get(id));
    }

}
