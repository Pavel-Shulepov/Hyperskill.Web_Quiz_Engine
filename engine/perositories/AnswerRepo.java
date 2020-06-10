package engine.perositories;

import engine.domain.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepo extends JpaRepository<AnswerEntity, Integer> {
}
