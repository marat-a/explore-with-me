package ru.practicum.mainserver.request.service;

import ru.practicum.mainserver.request.model.RequestDto;

import java.util.List;


public interface RequestService {
    RequestDto addRequest(long userId, long eventId);

    RequestDto cancelRequest(long userId, long requestId);

    List<RequestDto> allUserRequests(long userId);

    List<RequestDto> getRequestsForEvent(long userId, long eventId);

    RequestDto confirmRequest(long userId, long eventId, long requestId);

    RequestDto rejectRequests(long userId, long eventId, long requestId);
}
