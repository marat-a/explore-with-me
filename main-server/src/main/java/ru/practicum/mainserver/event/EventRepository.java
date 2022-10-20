package ru.practicum.mainserver.event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainserver.event.model.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAll(Specification<Event> spec, Pageable page);

    List<Event> findByInitiatorId(Long initiatorId, Pageable page);
}
