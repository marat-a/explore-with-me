package ru.practicum.mainserver.event.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.mainserver.category.model.CategoryDto;
import ru.practicum.mainserver.common.enums.EventState;
import ru.practicum.mainserver.user.model.UserShortDto;

@Data
@NoArgsConstructor
public class EventFullDto {
    private Long id;

    private String annotation;

    private CategoryDto category;

    private Long confirmedRequests;

    private String createdOn;

    private String description;

    private String eventDate;

    private UserShortDto initiator;

    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private String publishedOn;

    private Boolean requestModeration;

    private EventState state;

    private String title;

    private Long views;
}
