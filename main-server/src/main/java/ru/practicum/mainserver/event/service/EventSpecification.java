package ru.practicum.mainserver.event.service;

import com.vividsolutions.jts.geom.Point;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.mainserver.category.model.Category;
import ru.practicum.mainserver.category.model.Category_;
import ru.practicum.mainserver.common.enums.EventState;
import ru.practicum.mainserver.event.model.Event;
import ru.practicum.mainserver.event.model.Event_;
import ru.practicum.mainserver.user.model.User;
import ru.practicum.mainserver.user.model.User_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.text.MessageFormat;
import java.time.LocalDateTime;

public class EventSpecification {
    static Specification<Event> eventDescriptionContainsText(String text) {
        return (root, query, builder) -> builder.like(root.get("description"),
                MessageFormat.format("%{0}%", text));
    }

    static Specification<Event> eventAnnotationContainsText(String text) {
        return (root, query, builder) -> builder.like(root.get("annotation"),
                MessageFormat.format("%{0}%", text));
    }

    static Specification<Event> eventIsPaid(Boolean paid) {
        return (root, query, builder) -> builder.equal(root.get("paid"), paid);
    }

    static Specification<Event> eventIsAvailable() {
        return (root, query, builder) -> builder.lessThan(root.get("confirmedRequest"), root.get("participantLimit"));
    }

    static Specification<Event> eventsBetween(LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        return (root, query, builder) -> builder.between(root.get("eventDate"), rangeStart, rangeEnd);
    }

    static Specification<Event> eventsDistanceLessThen(Point point, Double distance) {
        return (root, query, builder) -> builder.isTrue(builder.function(
                "ST_DWithin",
                Boolean.class,
                root.get(Event_.coordinates),
                builder.literal(point),
                builder.literal(distance)));
    }

    static Specification<Event> eventsAfter() {
        return (root, query, builder) -> builder.greaterThan(root.get("eventDate"), LocalDateTime.now());
    }

    static Specification<Event> eventInCategories(Integer[] categories) {
        return (root, query, builder) -> {
            Join<Event, Category> categoryJoin = root.join(Event_.category, JoinType.LEFT);
            CriteriaBuilder.In<Long> inClause = builder.in(categoryJoin.get(Category_.id));
            if (categories.length >= 1) {
                for (long categoryId : categories) {
                    inClause.value(categoryId);
                }
            }
            return inClause;
        };
    }

    static Specification<Event> eventInUsers(Integer[] users) {
        return (root, query, builder) -> {
            Join<Event, User> userJoin = root.join(Event_.initiator, JoinType.LEFT);
            CriteriaBuilder.In<Long> inClause = builder.in(userJoin.get(User_.id));
            if (users.length >= 1) {
                for (long userId : users) {
                    inClause.value(userId);
                }
            }

            return inClause;
        };
    }

    static Specification<Event> eventInStates(EventState[] states) {
        return (root, query, builder) -> root.get("state").in((Object[]) states);
    }
}
