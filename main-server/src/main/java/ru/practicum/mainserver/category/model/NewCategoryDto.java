package ru.practicum.mainserver.category.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class NewCategoryDto {
    @NotBlank
    @Size(min = 3, max = 120)
    private String name;
}
