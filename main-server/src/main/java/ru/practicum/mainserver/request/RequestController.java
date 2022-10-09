package ru.practicum.mainserver.request;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.request.model.RequestDto;
import ru.practicum.mainserver.request.service.RequestService;

import java.util.List;

@RestController
@AllArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @PostMapping("/users/{userId}/requests")
    public RequestDto addRequest(@PathVariable long userId, @RequestParam long eventId) {
        return requestService.addRequest(userId, eventId);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public RequestDto cancelRequestByInitiator(@PathVariable long userId, @PathVariable long requestId) {
        return requestService.cancelRequest(userId, requestId);
    }

    @GetMapping("/users/{userId}/requests")
    public List<RequestDto> getAllRequestForUser(@PathVariable long userId) {
        return requestService.allUserRequests(userId);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<RequestDto> getUserEventRequests(@PathVariable long userId, @PathVariable long eventId) {
        return requestService.getRequestsForEvent(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests/{requestId}/confirm")
    public RequestDto confirmUserRequests(@PathVariable long userId, @PathVariable long eventId,
                                          @PathVariable long requestId) {
        return requestService.confirmRequest(userId, eventId, requestId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests/{requestId}/reject")
    public RequestDto rejectUserRequests(@PathVariable long userId, @PathVariable long eventId,
                                         @PathVariable long requestId) {
        return requestService.rejectRequests(userId, eventId, requestId);
    }
}