package ru.practicum.mainserver.event.model;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.mainserver.category.CategoryService;
import ru.practicum.mainserver.category.model.CategoryMapper;
import ru.practicum.mainserver.client.StatsClient;
import ru.practicum.mainserver.common.enums.EventState;
import ru.practicum.mainserver.location.model.CoordinatesMapper;
import ru.practicum.mainserver.user.model.UserMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventMapper {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    StatsClient statsClient;

    @Autowired
    public EventMapper(StatsClient statsClient) {
        this.statsClient = statsClient;
    }

    public EventShortDto toEventShortDto(Event event) {
        EventShortDto eventShortDto = new EventShortDto();
        eventShortDto.setId(event.getId());
        eventShortDto.setEventDate(event.getEventDate().format(FORMATTER));
        eventShortDto.setPaid(event.getPaid());
        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setAnnotation(event.getAnnotation());
        eventShortDto.setCategory(CategoryMapper.toCategoryDto(event.getCategory()));
        eventShortDto.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
        eventShortDto.setConfirmedRequests(event.getConfirmedRequests());
        eventShortDto.setViews(event.getViews() == null ? statsClient.getEventViews(event) : event.getViews());
        return eventShortDto;
    }

    public EventFullDto toEventFullDto(Event event) {
        EventFullDto eventFullDto = new EventFullDto();
        eventFullDto.setId(event.getId());
        eventFullDto.setEventDate(event.getEventDate().format(FORMATTER));
        eventFullDto.setPaid(event.getPaid());
        eventFullDto.setTitle(event.getTitle());
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setCategory(CategoryMapper.toCategoryDto(event.getCategory()));
        eventFullDto.setInitiator(UserMapper.toUserShortDto(event.getInitiator()));
        eventFullDto.setConfirmedRequests(event.getConfirmedRequests());
        eventFullDto.setViews(event.getViews() == null ? statsClient.getEventViews(event) : event.getViews());
        eventFullDto.setCreatedOn(event.getCreatedOn().format(FORMATTER));
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setCoordinates(CoordinatesMapper.pointToCoordinates(event.getCoordinates()));
        eventFullDto.setParticipantLimit(event.getParticipantLimit());
        eventFullDto.setPublishedOn(event.getPublishedOn() == null ? null : event.getPublishedOn().format(FORMATTER));
        eventFullDto.setRequestModeration(event.getRequestModeration());
        eventFullDto.setState(event.getState());
        eventFullDto.setTitle(event.getTitle());
        return eventFullDto;
    }

    public Event newToEvent(NewEventDto newEventDto, CategoryService categoryService) {
        Event event = new Event();
        event.setCoordinates(newEventDto.getCoordinates() == null ? null : CoordinatesMapper.coordinatesToPoint(newEventDto.getCoordinates()));
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(categoryService.getCategory(newEventDto.getCategory()));
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(newEventDto.getEventDate());
        event.setPaid(newEventDto.getPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setTitle(newEventDto.getTitle());
        event.setRequestModeration(newEventDto.getRequestModeration());
        return event;
    }


    public Event adminUpdateRequestToEvent(AdminUpdateEventRequest updateEventRequest, CategoryService categoryService) {
        Event event = new Event();
        event.setAnnotation(updateEventRequest.getAnnotation());
        event.setCategory(categoryService.getCategory(updateEventRequest.getCategory()));
        event.setDescription(updateEventRequest.getDescription());
        event.setEventDate(LocalDateTime.parse(updateEventRequest.getEventDate(), FORMATTER));
        event.setCoordinates(CoordinatesMapper.coordinatesToPoint(updateEventRequest.getCoordinates()));
        event.setPaid(updateEventRequest.getPaid());
        event.setParticipantLimit(updateEventRequest.getParticipantLimit());
        event.setTitle(updateEventRequest.getTitle());
        return event;
    }

    public Event updateRequestToEvent(UpdateEventRequest updateEventRequest, CategoryService categoryService) {
        Event event = new Event();
        event.setAnnotation(updateEventRequest.getAnnotation());
        event.setCategory(categoryService.getCategory(updateEventRequest.getCategory()));
        event.setDescription(updateEventRequest.getDescription());
        event.setEventDate(updateEventRequest.getEventDate());
        event.setPaid(updateEventRequest.getPaid());
        event.setParticipantLimit(updateEventRequest.getParticipantLimit());
        event.setTitle(updateEventRequest.getTitle());
        event.setState(EventState.PENDING);
        return event;
    }

    public List<EventFullDto> toEventFullDtoList(List<Event> events) {
        return events.stream().map(this::toEventFullDto).collect(Collectors.toList());
    }

    public List<EventShortDto> toEventShortDtoList(List<Event> events) {
        return events.stream().map(this::toEventShortDto).collect(Collectors.toList());
    }
}