package engine;

import engine.Entity.Completions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompletionsRepository extends PagingAndSortingRepository<Completions, Long> {

    @Query("SELECT c From Completions c WHERE userId = ?1 ORDER BY completedAt DESC")
    Page<Completions> findAllByUser(Integer userId, Pageable pageable);
}
