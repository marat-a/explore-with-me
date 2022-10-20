package ru.practicum.mainserver.location.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.common.enums.SortType;
import ru.practicum.mainserver.common.validators.SortTypeSubset;
import ru.practicum.mainserver.event.model.EventFullDto;
import ru.practicum.mainserver.location.model.LocationDto;
import ru.practicum.mainserver.location.service.LocationService;

import java.util.List;

@RestController
@RequestMapping(path = "/locations")
@AllArgsConstructor
public class PublicLocationController {

    private final LocationService locationService;

    @GetMapping
    List<LocationDto> getCategories(@RequestParam(defaultValue = "0") Integer from,
                                    @RequestParam(defaultValue = "10") Integer size) {
        return locationService.getLocations(from, size);
    }

    @GetMapping("/{locId}")
    List<EventFullDto> getLocation(@RequestParam(required = false) String text,
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
                                   @PathVariable Long locId) {
        return locationService.getLocation(
                text,
                categories,
                paid,
                onlyAvailable,
                rangeStart,
                rangeEnd,
                sort,
                from,
                size,
                locId);
    }
}