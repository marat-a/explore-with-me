package ru.practicum.mainserver.location.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class LocationDto {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Coordinates coordinates;

    private Double radius;
}