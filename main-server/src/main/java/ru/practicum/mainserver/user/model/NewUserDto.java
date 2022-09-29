package ru.practicum.mainserver.user.model;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class NewUserDto {
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
}
