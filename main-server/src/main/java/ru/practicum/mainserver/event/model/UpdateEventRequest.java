package ru.practicum.mainserver.event.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateEventRequest   {
  private String annotation;

  private Long category;

  private String description;

  private String eventDate;

  private Long eventId;

  private Boolean paid;

  private Integer participantLimit;

  private String title;
}
