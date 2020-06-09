package engine.services;

import engine.domain.Answer;
import engine.domain.Quiz;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {

    private final Map<Integer, Quiz> dataBase = new HashMap<>();

    public Quiz add(Quiz quiz) {
        int id = dataBase.size() + 1;
        quiz.setId(id);
        dataBase.put(id, quiz);
        return quiz;
    }

    public Quiz get(int id) {
        return dataBase.get(id);
    }

    public Collection<Quiz> all() {
        return dataBase.values();
    }

    public boolean solve(int id, Answer answer) {
        var a = dataBase.get(id).getAnswer();
        if (a == null) return true;
        var b = answer.getAnswer();
        Arrays.sort(a);
        Arrays.sort(b);
        return Arrays.equals(a, b);
    }

}
