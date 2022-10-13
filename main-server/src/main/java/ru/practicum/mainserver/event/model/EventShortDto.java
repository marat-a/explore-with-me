package ru.practicum.mainserver.event.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.mainserver.category.model.CategoryDto;
import ru.practicum.mainserver.user.model.UserShortDto;

@Getter
@Setter
public class EventShortDto   {
  private String annotation;

  private CategoryDto category;

  private Long confirmedRequests;

  private String eventDate;

  private Long id;

  private UserShortDto initiator;

  private Boolean paid;

  private String title;

  private Long views;
}
