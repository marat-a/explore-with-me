package ru.practicum.mainserver.event.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParticipationRequestDto   {
  private String created;

  private Long event;

  private Long id;

  private Long requester;

  private String status;
}
