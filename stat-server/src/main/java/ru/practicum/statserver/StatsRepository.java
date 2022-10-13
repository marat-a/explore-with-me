package ru.practicum.statserver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.statserver.model.EndpointHit;
import ru.practicum.statserver.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Long> {
    @Query(value =
            "SELECT app AS app, uri AS uri, COUNT(ip) AS hits FROM EndpointHit WHERE timestamp >= :start AND timestamp < :end GROUP BY app, uri"
    )
    List<ViewStats> countHits(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value =
            "SELECT app AS app, uri AS uri, COUNT(DISTINCT ip) AS hits FROM  EndpointHit WHERE timestamp >= :start AND timestamp < :end GROUP BY app, uri"
    )
    List<ViewStats> countUniqueHits(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}