package ru.practicum.mainserver.compilation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NewCompilationDto {

    private List<Long> events;

    private Boolean pinned;
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;
}
