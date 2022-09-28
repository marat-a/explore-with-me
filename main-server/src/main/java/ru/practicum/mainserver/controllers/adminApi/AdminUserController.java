package ru.practicum.mainserver.controllers.adminApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.user.UserService;
import ru.practicum.mainserver.user.model.User;
import ru.practicum.mainserver.user.model.UserDto;
import ru.practicum.mainserver.user.model.UserMapper;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers() {
        log.info("Get user's list");
        List<User> userList = userService.getUsers();
        return UserMapper.getUserDtoList(userList);
    }

    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) {
        log.info("Add user {}", userDto);
        User user = UserMapper.toUser(userDto);
        user = userService.addUser(user);
        return UserMapper.toUserDto(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        log.info("Удаление пользователя с userId={}", userId);
        userService.deleteUser(userId);
    }
}
