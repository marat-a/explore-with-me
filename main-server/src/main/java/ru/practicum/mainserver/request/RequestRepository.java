package ru.practicum.mainserver.request;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainserver.request.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByRequestorId(long userId);

    List<Request> findByEventId(long eventId);
}
