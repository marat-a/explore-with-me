package ru.practicum.mainserver.location;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainserver.location.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
