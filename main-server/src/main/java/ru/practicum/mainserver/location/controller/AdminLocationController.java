package ru.practicum.mainserver.location.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.location.model.LocationDto;
import ru.practicum.mainserver.location.service.LocationService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/locations")
@AllArgsConstructor
public class AdminLocationController {

    private final LocationService locationService;

    @PostMapping
    public LocationDto create(@RequestBody @Valid LocationDto locationDto) {
        return locationService.add(locationDto);
    }

    @PatchMapping
    public LocationDto updateLocation(@RequestBody LocationDto locationDto) {
        return locationService.update(locationDto);
    }

    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable long id) {
        locationService.delete(id);
    }

}