package ru.practicum.mainserver.event.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.mainserver.client.StatsClient;
import ru.practicum.mainserver.common.enums.SortType;
import ru.practicum.mainserver.common.validators.SortTypeSubset;
import ru.practicum.mainserver.event.model.EventFullDto;
import ru.practicum.mainserver.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@AllArgsConstructor
@RestController
public class PublicEventController {

    EventService eventService;
    StatsClient statsClient;

    @GetMapping("/events")
    public List<EventFullDto> getEventWithFilter(@RequestParam(required = false) String text,
                                                 @RequestParam(required = false) Integer[] categories,
                                                 @RequestParam(required = false) Boolean paid,
                                                 @RequestParam(required = false) Boolean onlyAvailable,
                                                 @RequestParam(required = false) String rangeStart,
                                                 @RequestParam(required = false) String rangeEnd,
                                                 @RequestParam(required = false)
                                                 @SortTypeSubset(anyOf = {
                                                         SortType.VIEWS,
                                                         SortType.EVENT_DATE,
                                                         SortType.DISTANCE}) SortType sort,
                                                 @RequestParam(required = false, defaultValue = "0") int from,
                                                 @RequestParam(required = false, defaultValue = "10") int size,
                                                 @RequestParam(required = false) Double lon,
                                                 @RequestParam(required = false) Double lat,
                                                 @RequestParam(required = false, defaultValue = "0") Double distance,
                                                 HttpServletRequest request) {

        statsClient.sendHit("App", request.getRequestURI(), request.getRemoteAddr());
        return eventService.getEventsWithFilter(
                text,
                categories,
                paid,
                onlyAvailable,
                rangeStart,
                rangeEnd,
                sort,
                from,
                size,
                lon,
                lat,
                distance
        );
    }

    @GetMapping("/events/{eventId}")
    public EventFullDto getPublishedEvent(@PathVariable long eventId, HttpServletRequest request) {
        statsClient.sendHit("App", request.getRequestURI(), request.getRemoteAddr());
        return eventService.getPublishedEvent(eventId);
    }
}
