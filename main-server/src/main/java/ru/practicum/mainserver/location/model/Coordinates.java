package ru.practicum.mainserver.location.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {
    @PositiveOrZero
    private Double lat;
    @PositiveOrZero
    private Double lon;
}

