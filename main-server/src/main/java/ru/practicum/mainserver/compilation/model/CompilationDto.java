package ru.practicum.mainserver.compilation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.mainserver.event.model.EventShortDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto   {
  private List<EventShortDto> events;

  private Long id;

  private Boolean pinned;

  private String title;  
}
