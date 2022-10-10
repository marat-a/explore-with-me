package ru.practicum.statserver.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statserver.StatsRepository;
import ru.practicum.statserver.model.EndpointHit;
import ru.practicum.statserver.model.EndpointHit_;
import ru.practicum.statserver.model.ViewStats;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class StatsServiceImpl implements StatsService {
    StatsRepository statsRepository;
    EntityManager entityManager;

    @Override
    public void handleHit(EndpointHit endpointHit) {
        statsRepository.save(endpointHit);
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root<EndpointHit> root = query.from(EndpointHit.class);
        Predicate[] predicates = new Predicate[2];

        predicates[0] = root.get(EndpointHit_.uri).in(uris);
        predicates[1] = builder.between(root.get(EndpointHit_.timestamp), start, end);

        query.where(predicates);

        if (unique) {
            query.multiselect(root.get(EndpointHit_.app), root.get(EndpointHit_.uri), builder.countDistinct(root.get(EndpointHit_.ip)));
        } else {
            query.multiselect(root.get(EndpointHit_.app), root.get(EndpointHit_.uri), builder.count(root));
        }

        query.groupBy(root.get(EndpointHit_.app), root.get(EndpointHit_.uri));

        return entityManager.createQuery(query).getResultStream().map(item -> ViewStats
                .builder()
                .app(item[0].toString())
                .uri(item[1].toString())
                .hits(Integer.valueOf(item[2].toString()))
                .build()).collect(Collectors.toList());
    }
}