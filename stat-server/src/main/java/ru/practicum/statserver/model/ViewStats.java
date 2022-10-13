package ru.practicum.statserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ViewStats {

    private String app;

    private String uri;

    private Integer hits;
}