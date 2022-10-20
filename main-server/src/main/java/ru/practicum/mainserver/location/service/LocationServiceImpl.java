package ru.practicum.mainserver.location.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.mainserver.common.enums.SortType;
import ru.practicum.mainserver.common.exceptions.NotFoundException;
import ru.practicum.mainserver.event.model.EventFullDto;
import ru.practicum.mainserver.event.service.EventService;
import ru.practicum.mainserver.location.LocationRepository;
import ru.practicum.mainserver.location.model.CoordinatesMapper;
import ru.practicum.mainserver.location.model.Location;
import ru.practicum.mainserver.location.model.LocationDto;
import ru.practicum.mainserver.location.model.LocationMapper;

import java.util.List;

@Service
@Slf4j
public class LocationServiceImpl implements LocationService {
    EventService eventService;
    LocationRepository locationRepository;

    @Override
    public LocationDto add(LocationDto locationDto) {
        Location location = LocationMapper.toLocation(locationDto);
        location = locationRepository.save(location);
        return LocationMapper.toLocationDto(location);
    }

    @Override
    public LocationDto update(LocationDto locationDto) {
            Location location = findById(locationDto.getId());
            location.setName(locationDto.getName() == null ? null : locationDto.getName());
            location.setCoordinates(locationDto.getCoordinates() == null ? null : CoordinatesMapper.coordinatesToPoint(locationDto.getCoordinates()));
            location.setRadius(locationDto.getRadius() == null ? null : locationDto.getRadius());
            return LocationMapper.toLocationDto(location);
    }

    @Override
    public void delete(long locationId) {
        if (isLocationExist(locationId)) {
           locationRepository.deleteById(locationId);
        } else throw new NotFoundException("Location not found");
    }

    Location findById(Long locationId) {
        return locationRepository.findById(locationId).orElseThrow(() -> new NotFoundException("Location not found"));
    }

    Boolean isLocationExist(Long locationId) {
        return locationRepository.existsById(locationId);
    }

    @Override
    public List<LocationDto> getLocations(Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from, size);
        List<Location> locations = locationRepository.findAll(pageRequest).toList();
        log.info("Categories was requested with parameters: from =" +from+ ", size=" + size + ".");
        return LocationMapper.toLocationDtoList(locations);
    }

    @Override
    public List<EventFullDto> getLocation(String text,
                                          Integer[] categories,
                                          Boolean paid,
                                          Boolean onlyAvailable,
                                          String rangeStart,
                                          String rangeEnd,
                                          SortType sort,
                                          int from,
                                          int size,
                                          Long locId) {
        Location location = locationRepository.findById(locId)
                .orElseThrow(() -> new NotFoundException("Location with id =" + locId + " not found."));
        log.info("Location with id " + locId + " was requested.");
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
                location.getCoordinates().getX(),
                location.getCoordinates().getY(),
                location.getRadius());

    }
}
