package ru.practicum.mainserver.compilation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.mainserver.compilation.model.Compilation;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    @Query(
            "SELECT c FROM Compilation AS c WHERE c.pinned = TRUE"
    )
    List<Compilation> findPinnedCompilations(Pageable page);

    @Query(
            "SELECT c FROM Compilation AS c WHERE c.pinned = FALSE"
    )
    List<Compilation> findCompilations(Pageable page);
}
