package ru.practicum.mainserver.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class NewUserDto {
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
}
