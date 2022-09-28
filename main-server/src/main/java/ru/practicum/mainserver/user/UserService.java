package ru.practicum.mainserver.user;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainserver.user.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User addUser(User user);

    void deleteUser(long userId);

    void checkUser(long userId);
}
