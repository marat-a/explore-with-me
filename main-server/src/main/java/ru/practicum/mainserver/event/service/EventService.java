package ru.practicum.mainserver.event.service;

import ru.practicum.mainserver.client.dto.ViewStats;
import ru.practicum.mainserver.common.enums.EventState;
import ru.practicum.mainserver.event.model.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface EventService {

    List<Event> getEventsWithFilter(String text, Integer[] categories, Boolean paid, Boolean onlyAvailable, String sort, String rangeStart, String rangeEnd, int from, int size);

    Event findById(Long id);

    EventFullDto getPublishedEvent(long eventId);

    EventFullDto getEventForInitiator(long userId, long eventId);

    List<EventFullDto> getAllEventForInitiator(long userId, int from, int size);

    EventFullDto updateEventByUser(UpdateEventRequest event, long userId);

    EventFullDto cancelEvent(long userId, long eventId);

    List<EventFullDto> getEventWithFilterForAdmin(Integer[] users, EventState[] states, Integer[] categories, String rangeStart, String rangeEnd, int from, int size);

    EventFullDto updateEventByAdmin(AdminUpdateEventRequest updateRequest, long eventId);

    EventFullDto publishEvent(long eventId);

    EventFullDto rejectEvent(long eventId);

    EventFullDto add(NewEventDto newEventDto, long userId);


}
