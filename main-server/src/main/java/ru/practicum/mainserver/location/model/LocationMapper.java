package ru.practicum.mainserver.location.model;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocationMapper {
    public static Location toLocation(LocationDto locationDto) {
        Location location = new Location();
        location.setId(locationDto.getId() == null ? null : locationDto.getId());
        location.setName(locationDto.getName() == null ? null : locationDto.getName());
        location.setCoordinates(locationDto.getCoordinates() == null ? null : CoordinatesMapper.coordinatesToPoint(locationDto.getCoordinates()));
        location.setRadius(locationDto.getRadius() == null ? null : locationDto.getRadius());
        return location;
    }

    public static LocationDto toLocationDto(Location location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(location.getId() == null ? null : location.getId());
        locationDto.setName(location.getName() == null ? null : location.getName());
        locationDto.setCoordinates(location.getCoordinates() == null ? null : CoordinatesMapper.pointToCoordinates(location.getCoordinates()));
        locationDto.setRadius(location.getRadius() == null ? null : location.getRadius());
        return locationDto;
    }

    public static List<LocationDto> toLocationDtoList(List<Location> locations) {
        return locations.stream().map(LocationMapper::toLocationDto).collect(Collectors.toList());
    }
}

