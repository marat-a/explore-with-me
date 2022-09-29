package ru.practicum.mainserver.user.service;

import ru.practicum.mainserver.user.model.User;
import ru.practicum.mainserver.user.model.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(long[] ids, int from, int size);

    UserDto addUser(User user);

    void deleteUser(long userId);


    boolean isUserExist(long userId);

    UserDto getUser(Long userId);
}
