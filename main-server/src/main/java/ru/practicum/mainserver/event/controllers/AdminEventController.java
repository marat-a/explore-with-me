package ru.practicum.mainserver.event.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.common.enums.EventState;
import ru.practicum.mainserver.event.model.AdminUpdateEventRequest;
import ru.practicum.mainserver.event.model.EventFullDto;
import ru.practicum.mainserver.event.service.EventService;

import java.util.List;

@RestController
@AllArgsConstructor
public class AdminEventController {

    EventService eventService;

    @GetMapping("/admin/events")
    public List<EventFullDto> getEventWithFilterForAdmin(@RequestParam(required = false) Integer[] users,
                                                         @RequestParam(required = false) EventState[] states,
                                                         @RequestParam(required = false) Integer[] categories,
                                                         @RequestParam(required = false) String rangeStart,
                                                         @RequestParam(required = false) String rangeEnd,
                                                         @RequestParam(required = false, defaultValue = "0") int from,
                                                         @RequestParam(required = false, defaultValue = "10") int size) {

        return eventService.getEventWithFilterForAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/admin/events/{eventId}")
    public EventFullDto updateEventByAdmin(@PathVariable long eventId, @RequestBody AdminUpdateEventRequest updateRequest) {
        return eventService.updateEventByAdmin(updateRequest, eventId);
    }

    @PatchMapping("/admin/events/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable long eventId) {
        return eventService.publishEvent(eventId);
    }

    @PatchMapping("/admin/events/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable long eventId) {
        return eventService.rejectEvent(eventId);
    }
}
