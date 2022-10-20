package ru.practicum.mainserver.location.service;

import ru.practicum.mainserver.common.enums.SortType;
import ru.practicum.mainserver.event.model.EventFullDto;
import ru.practicum.mainserver.location.model.LocationDto;

import java.util.List;

public interface LocationService {
    LocationDto add(LocationDto locationDto);
    LocationDto update(LocationDto locationDto);
    void delete(long id);
    List<LocationDto> getLocations(Integer from, Integer size);

    List<EventFullDto> getLocation(String text,
                                   Integer[] categories,
                                   Boolean paid,
                                   Boolean onlyAvailable,
                                   String rangeStart,
                                   String rangeEnd,
                                   SortType sort,
                                   int from,
                                   int size,
                                   Long locId);
}
