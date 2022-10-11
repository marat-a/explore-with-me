package ru.practicum.mainserver.event.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.mainserver.client.StatsClient;
import ru.practicum.mainserver.event.model.Event;
import ru.practicum.mainserver.event.model.EventFullDto;
import ru.practicum.mainserver.event.model.EventMapper;
import ru.practicum.mainserver.event.model.EventShortDto;
import ru.practicum.mainserver.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@AllArgsConstructor
@RestController
public class PublicEventController {

    EventService eventService;
    StatsClient statsClient;
    EventMapper eventMapper;

    @GetMapping("/events")
    public List<EventShortDto> getEventWithFilter(@RequestParam(required = false) String text,
                                                  @RequestParam(required = false) Integer[] categories,
                                                  @RequestParam(required = false) Boolean paid,
                                                  @RequestParam(required = false) String rangeStart,
                                                  @RequestParam(required = false) String rangeEnd,
                                                  @RequestParam(required = false) Boolean onlyAvailable,
                                                  @RequestParam(required = false) String sort,
                                                  @RequestParam(required = false, defaultValue = "0") int from,
                                                  @RequestParam(required = false, defaultValue = "10") int size,
                                                  HttpServletRequest request) {
        List<Event> events = eventService.getEventsWithFilter(
                text,
                categories,
                paid,
                onlyAvailable,
                sort,
                rangeStart,
                rangeEnd,
                from,
                size
        );
        statsClient.sendHit("App", request.getRequestURI(), request.getRemoteAddr());
        return eventMapper.toEventShortDtoList(events);
    }

    @GetMapping("/events/{eventId}")
    public EventFullDto getPublishedEvent(@PathVariable long eventId, HttpServletRequest request) {
        statsClient.sendHit("App", request.getRequestURI(), request.getRemoteAddr());
        return eventService.getPublishedEvent(eventId);
    }
}
