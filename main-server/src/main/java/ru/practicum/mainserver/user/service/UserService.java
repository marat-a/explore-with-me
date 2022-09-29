package ru.practicum.mainserver.user.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainserver.user.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers(long[] ids, int from, int size);

    User addUser(User user);

    void deleteUser(long userId);


    boolean isUserExist(long userId);

    User getUser(Long userId);
}
