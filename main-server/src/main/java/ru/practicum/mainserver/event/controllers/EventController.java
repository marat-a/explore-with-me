package ru.practicum.mainserver.event.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.event.model.EventFullDto;
import ru.practicum.mainserver.event.model.NewEventDto;
import ru.practicum.mainserver.event.model.UpdateEventRequest;
import ru.practicum.mainserver.event.service.EventService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class EventController {

    private final EventService eventService;

    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getEventForUser(@PathVariable long userId,
                                        @PathVariable long eventId) {
        return eventService.getEventForInitiator(userId, eventId);
    }

    @GetMapping("/users/{userId}/events")
    public List<EventFullDto> getEvents(@PathVariable long userId,
                                        @RequestParam(required = false, defaultValue = "0") int from,
                                        @RequestParam(required = false, defaultValue = "20") int size) {
        return eventService.getAllEventForInitiator(userId, from, size);
    }

    @PatchMapping("/users/{userId}/events")
    public EventFullDto updateEventByUser(@PathVariable long userId,
                                           @Valid @RequestBody UpdateEventRequest updateEventRequest) {
        return eventService.updateEventByUser(updateEventRequest, userId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto cancellation(@PathVariable long userId,
                                     @PathVariable long eventId) {
        return eventService.cancelEvent(userId, eventId);
    }

    @PostMapping("/users/{userId}/events")
    public EventFullDto create(@PathVariable long userId, @Valid @RequestBody NewEventDto newEventDto) {
        return eventService.add(newEventDto, userId);
    }

}
