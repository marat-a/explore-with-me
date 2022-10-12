package ru.practicum.mainserver.event.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.mainserver.category.CategoryService;
import ru.practicum.mainserver.category.model.Category;
import ru.practicum.mainserver.category.model.Category_;
import ru.practicum.mainserver.client.StatsClient;
import ru.practicum.mainserver.common.enums.EventState;
import ru.practicum.mainserver.common.enums.SortType;
import ru.practicum.mainserver.event.EventRepository;
import ru.practicum.mainserver.event.model.*;
import ru.practicum.mainserver.user.model.User;
import ru.practicum.mainserver.user.model.User_;
import ru.practicum.mainserver.user.service.UserService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    EventRepository eventRepository;
    UserService userService;
    CategoryService categoryService;
    StatsClient statsClient;
    EventMapper eventMapper;


    @Override
    public EventFullDto add(NewEventDto newEventDto, long userId) {
        Event event = eventMapper.newToEvent(newEventDto, categoryService);
        event.setInitiator(userService.findUserById(userId));
        event.setCreatedOn(LocalDateTime.now());
        event.setConfirmedRequests(0L);
        event.setState(EventState.PENDING);
        event.setViews(0L);
        return eventMapper.toEventFullDto(eventRepository.save(event));
    }


    public Event updateEvent(Event event, Event updateRequest) {
        if (updateRequest.getEventDate() != null) {
            event.setEventDate(updateRequest.getEventDate());
        }
        if (updateRequest.getAnnotation() != null) {
            event.setAnnotation(updateRequest.getAnnotation());
        }
        if (updateRequest.getCategory() != null) {
            event.setCategory(updateRequest.getCategory());
        }
        if (updateRequest.getDescription() != null) {
            event.setDescription(updateRequest.getDescription());
        }
        if (updateRequest.getPaid() != null) {
            event.setPaid(updateRequest.getPaid());
        }
        if (updateRequest.getLocation() != null) {
            event.setLocation(updateRequest.getLocation());
        }
        if (updateRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateRequest.getParticipantLimit());
        }
        if (updateRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateRequest.getRequestModeration());
        }
        if (updateRequest.getTitle() != null) {
            event.setTitle(updateRequest.getTitle());
        }

        return eventRepository.save(event);
    }

    public void checkPermission(Event event, Long userId) {
        if (!((event.getState().equals(EventState.CANCELED)
                || (event.getState().equals(EventState.PENDING)))
                || !event.getRequestModeration().equals(Boolean.TRUE))) { //Проверка пользователя для разрешения правок
            throw new RuntimeException();
        } else if (!Objects.equals(event.getInitiator().getId(), userId)) {
            throw new RuntimeException();
        }
    }

    @Override
    public EventFullDto updateEventByUser(UpdateEventRequest updateEventRequest, long userId) {
        Event updateRequest = eventMapper.updateRequestToEvent(updateEventRequest, categoryService);
        Event event = findById(updateEventRequest.getEventId());
        checkPermission(event, userId);
        Event updatedEvent = updateEvent(event, updateRequest);
        updatedEvent.setState(EventState.PENDING);
        return eventMapper.toEventFullDto(updatedEvent);
    }

    @Override
    public EventFullDto updateEventByAdmin(AdminUpdateEventRequest updateRequest, long eventId) {
        Event updateRequestEvent = eventMapper.adminUpdateRequestToEvent(updateRequest, categoryService);
        Event event = findById(eventId);
        return eventMapper.toEventFullDto(updateEvent(event, updateRequestEvent));
    }

    @Override
    public EventFullDto publishEvent(long eventId) {
        Event event = findById(eventId);
        event.setState(EventState.PUBLISHED);
        return eventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto rejectEvent(long eventId) {
        Event event = findById(eventId);
        event.setState(EventState.CANCELED);
        return eventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto cancelEvent(long userId, long eventId) {
        Event event = findById(eventId);
        if (!event.getRequestModeration() || !Objects.equals(event.getInitiator().getId(), userId)) {
            throw new RuntimeException();
        }
        event.setState(EventState.CANCELED);
        return eventMapper.toEventFullDto(eventRepository.save(event));
    }


    @Override
    public Event findById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            return event.get();
        } else throw new RuntimeException("event id= " + id + " not found");
    }

    @Override
    public List<EventFullDto> getAllEventForInitiator(long initiatorId, int from, int size) {
        userService.isUserExist(initiatorId);
        Sort sortById = Sort.by(Sort.Direction.ASC, "id");
        Pageable page = PageRequest.of(from, size, sortById);
        List<Event> eventList = eventRepository.findByInitiatorId(initiatorId, page);
        return eventMapper.toEventFullDtoList(eventList);
    }

    @Override
    public EventFullDto getEventForInitiator(long userId, long eventId) {
        Event event = findById(eventId);
        if (!Objects.equals(userId, event.getInitiator().getId())) {
            throw new RuntimeException();
        }
        return eventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto getPublishedEvent(long idEvent) {
        Event event = findById(idEvent);
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new RuntimeException();
        }
        return eventMapper.toEventFullDto(findById(idEvent));
    }

    private Specification<Event> eventDescriptionContainsText(String text) {
        return (root, query, builder) -> builder.like(root.get("description"),
                MessageFormat.format("%{0}%", text));
    }

    private Specification<Event> eventAnnotationContainsText(String text) {
        return (root, query, builder) -> builder.like(root.get("annotation"),
                MessageFormat.format("%{0}%", text));
    }

    private Specification<Event> eventIsPaid(Boolean paid) {
        return (root, query, builder) -> builder.equal(root.get("paid"), paid);
    }

    private Specification<Event> eventIsAvailable() {
        return (root, query, builder) -> builder.lessThan(root.get("confirmedRequest"), root.get("participantLimit"));
    }

    private Specification<Event> eventsBetween(LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        return (root, query, builder) -> builder.between(root.get("eventDate"), rangeStart, rangeEnd);
    }

    private Specification<Event> eventsAfter() {
        return (root, query, builder) -> builder.greaterThan(root.get("eventDate"), LocalDateTime.now());
    }

    private Specification<Event> eventInCategories(Integer[] categories) {
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

    private Specification<Event> eventInUsers(Integer[] users) {
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

    private Specification<Event> eventInStates(EventState[] states) {
        return (root, query, builder) -> root.get("state").in((Object[]) states);
    }

    public List<Event> getEventsWithFilter(String text, Integer[] categories, Boolean paid, Boolean onlyAvailable,
                                           SortType sort, String rangeStart, String rangeEnd, int from, int size) {
        if (sort == null) {
            throw new RuntimeException();
        }
        Pageable page;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Specification<Event> spec = getEventSpecification(
                text,
                categories,
                paid,
                onlyAvailable,
                rangeStart,
                rangeEnd, formatter);
        List<Event> events;
        switch (sort) {
            case EVENT_DATE:
                page = PageRequest.of(from, size, Sort.by(Sort.Direction.ASC, "eventDate"));
                events = eventRepository.findAll(spec, page);
                return statsClient.addViews(events);
            case VIEWS:
                page = PageRequest.of(from, size);
                events = eventRepository.findAll(spec, page);
                statsClient.addViews(events);
                events.sort(Comparator.comparing(Event::getViews));
                return events;
            default:
                throw new RuntimeException();
        }
    }

    private Specification<Event> getEventSpecification(String text,
                                                       Integer[] categories,
                                                       Boolean paid,
                                                       Boolean onlyAvailable,
                                                       String rangeStart,
                                                       String rangeEnd,
                                                       DateTimeFormatter formatter) {

        Specification<Event> containsText = text == null ? null : eventDescriptionContainsText(text)
                .or(eventAnnotationContainsText(text));
        Specification<Event> isPaid = paid == null ? null : eventIsPaid(paid);
        Specification<Event> isAvailable = onlyAvailable == null || !onlyAvailable ? null : eventIsAvailable();
        Specification<Event> hasCategory = categories == null ? null : eventInCategories(categories);
        Specification<Event> dateRange = (rangeStart == null || rangeEnd == null) ?
                eventsAfter() : eventsBetween(
                LocalDateTime.parse(rangeStart, formatter),
                LocalDateTime.parse(rangeEnd, formatter));
        return Specification.where(containsText)
                .and(isPaid)
                .and(hasCategory)
                .and(isAvailable)
                .and(dateRange);
    }

    public List<EventFullDto> getEventWithFilterForAdmin(Integer[] users, EventState[] states, Integer[] categories,
                                                         String rangeStart, String rangeEnd, int from, int size) {
        Pageable page = PageRequest.of(from, size, Sort.by(Sort.Direction.ASC, "eventDate"));
        Specification<Event> hasCategory = categories == null ? null : eventInCategories(categories);
        Specification<Event> hasUsers = users == null ? null : eventInUsers(users);
        Specification<Event> hasStates = states == null ? null : eventInStates(states);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Specification<Event> dateRange = (rangeStart == null || rangeEnd == null) ?
                eventsAfter() : eventsBetween(
                LocalDateTime.parse(rangeStart, formatter),
                LocalDateTime.parse(rangeEnd, formatter));
        Specification<Event> spec = Specification.where(hasCategory)
                .and(hasUsers)
                .and(hasStates)
                .and(dateRange);
        return eventMapper.toEventFullDtoList(eventRepository.findAll(spec, page));
    }
}
