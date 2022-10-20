package ru.practicum.mainserver.event.model;

import lombok.*;
import ru.practicum.mainserver.category.model.CategoryDto;
import ru.practicum.mainserver.common.enums.EventState;
import ru.practicum.mainserver.location.model.Coordinates;
import ru.practicum.mainserver.user.model.UserShortDto;

@Getter
@Setter
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

    private Coordinates coordinates;

    private Boolean paid;

    private Long participantLimit;

    private String publishedOn;

    private Boolean requestModeration;

    private EventState state;

    private String title;

    private Long views;

}
