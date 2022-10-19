package ru.practicum.mainserver.event.model;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.mainserver.category.CategoryService;
import ru.practicum.mainserver.category.model.CategoryMapper;
import ru.practicum.mainserver.client.StatsClient;
import ru.practicum.mainserver.common.enums.EventState;
import ru.practicum.mainserver.location.Location;
import ru.practicum.mainserver.user.model.UserMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Component
@AllArgsConstructor
@RequiredArgsConstructor

public class EventMapper {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    StatsClient statsClient;
    public final int SRID = 4326;
    public GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), SRID);



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
        eventFullDto.setLocation(pointToLocation(event.getCoordinates()));
        eventFullDto.setParticipantLimit(event.getParticipantLimit());
        eventFullDto.setPublishedOn(event.getPublishedOn() == null ? null : event.getPublishedOn().format(FORMATTER));
        eventFullDto.setRequestModeration(event.getRequestModeration());
        eventFullDto.setState(event.getState());
        eventFullDto.setTitle(event.getTitle());
        return eventFullDto;
    }

    public Event newToEvent(NewEventDto newEventDto, CategoryService categoryService) {
        Event event = new Event();
        event.setCoordinates(newEventDto.getLocation() == null ? null : locationToPoint(newEventDto.getLocation()));
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

    public Point locationToPoint(Location location) {
        if (location == null) return null;
        else return geometryFactory.createPoint(new Coordinate(location.getLon(), location.getLat()));
    }

    public Location pointToLocation(Point point) {
        if (point == null) return null;
        else return new Location(point.getY(), point.getX());
    }


    public Event adminUpdateRequestToEvent(AdminUpdateEventRequest updateEventRequest, CategoryService categoryService) {
        Event event = new Event();

        event.setAnnotation(updateEventRequest.getAnnotation());
        event.setCategory(categoryService.getCategory(updateEventRequest.getCategory()));
        event.setDescription(updateEventRequest.getDescription());
        event.setEventDate(LocalDateTime.parse(updateEventRequest.getEventDate(), FORMATTER));
        event.setCoordinates(locationToPoint(updateEventRequest.getLocation()));
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