package engine.perositories;

import engine.domain.Complete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompleteRepo extends JpaRepository<Complete, Integer> {

    Page<Complete> findAllByUserEmail(Pageable page, String email);

}
