package ru.practicum.mainserver.user.model;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static User toUser(NewUserDto newUserDto) {
        return new User(
                null,
                newUserDto.getName(),
                newUserDto.getEmail()
        );
    }

    public static List<UserDto> getUserDtoList(List<User> userList) {
        return userList.stream()
                .map(UserMapper::toUserDto)
                .sorted(Comparator.comparing(UserDto::getId))
                .collect(Collectors.toList());
    }
}