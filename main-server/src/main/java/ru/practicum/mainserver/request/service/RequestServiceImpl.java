package ru.practicum.mainserver.request.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.mainserver.common.enums.EventState;
import ru.practicum.mainserver.event.model.Event;
import ru.practicum.mainserver.event.service.EventService;
import ru.practicum.mainserver.request.RequestRepository;
import ru.practicum.mainserver.request.model.Request;
import ru.practicum.mainserver.request.model.RequestDto;
import ru.practicum.mainserver.request.model.RequestMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final EventService eventService;
    
    @Override
    public RequestDto addRequest(long userId, long eventId) {
        Event event = eventService.findById(eventId);
        if (event.getInitiator().getId() == userId || !EventState.PUBLISHED.equals(event.getState())) { //Условие опубликованности
            throw new RuntimeException();
        }
        if (event.getParticipantLimit() != 0) {
            if (event.getParticipantLimit() <= event.getConfirmedRequests()) {
                throw new RuntimeException();
            }
        }
        Request request = new Request();
        request.setCreated(LocalDateTime.now());
        request.setEventId(eventId);
        request.setRequestorId(userId);
        if (!event.getRequestModeration()) {
            request.setStatus(EventState.PUBLISHED);
        } else {
            request.setStatus(EventState.PENDING);
        }
        return RequestMapper.toRequestDto(requestRepository.save(request));
    }

    @Override
    public RequestDto cancelRequest(long userId, long requestId) {
        Request request = findById(requestId);
        if (request.getRequestorId() != userId) {
            throw new RuntimeException();
        }
        request.setStatus(EventState.CANCELED);
        return RequestMapper.toRequestDto(request);
    }

    @Override
    public List<RequestDto> allUserRequests(long userId) {
        return RequestMapper.toRequestDtoList(requestRepository.findByRequestorId(userId));
    }

    @Override
    public List<RequestDto> getRequestsForEvent(long userId, long eventId) {
        Event event = eventService.findById(eventId);
        if (event.getInitiator().getId() != userId) {
            throw new RuntimeException();
        } else
            return RequestMapper.toRequestDtoList(requestRepository.findByEventId(eventId));

    }

    @Override
    public RequestDto confirmRequest(long userId, long eventId, long requestId) {
        Event event = eventService.findById(eventId);
        Request request = findById(requestId);
        if ((event.getParticipantLimit() == 0 || (event.getParticipantLimit() > event.getConfirmedRequests()))
                && Objects.equals(event.getInitiator().getId(), userId)) {
            request.setStatus(EventState.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            requestRepository.save(request);
            if (Objects.equals(event.getConfirmedRequests(), event.getParticipantLimit())
                    || event.getParticipantLimit() != 0) {
                List<Request> requests = requestRepository.findByEventId(eventId);
                for (Request req : requests) {
                    if (req.getStatus().equals(EventState.PENDING)) {
                        req.setStatus(EventState.REJECTED);
                        requestRepository.save(req);
                    }
                }
            }
            return RequestMapper.toRequestDto(request);
        }
        throw new RuntimeException();
    }

    @Override
    public RequestDto rejectRequests(long userId, long eventId, long requestId) {
        Request request = findById(requestId);
        request.setStatus(EventState.REJECTED);
        return RequestMapper.toRequestDto(request);
    }

    public Request findById(Long id) {
        Optional<Request> r = requestRepository.findById(id);
        if (r.isPresent()) {
            return r.get();
        } else {
            throw new RuntimeException();
        }
    }
}
