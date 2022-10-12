package ru.practicum.mainserver.compilation.model;

import lombok.*;
import ru.practicum.mainserver.event.model.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto   {
  private List<EventShortDto> events;

  private Long id;

  private Boolean pinned;

  @NotBlank
  @Size(min = 3, max = 120)
  private String title;  
}
