package ru.practicum.mainserver.request.model;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequestMapper {
    public static RequestDto toRequestDto(Request request) {
        RequestDto requestDto = new RequestDto();
        requestDto.setId(request.getId());
        requestDto.setEvent(request.getEventId());
        requestDto.setRequester(request.getRequestorId());
        requestDto.setStatus(request.getStatus());
        requestDto.setCreated(request.getCreated());
        return requestDto;
    }

    public static List<RequestDto> toRequestDtoList(List<Request> requests) {
        return requests.stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }
}