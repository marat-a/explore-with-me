package ru.practicum.mainserver.event;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainserver.event.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
