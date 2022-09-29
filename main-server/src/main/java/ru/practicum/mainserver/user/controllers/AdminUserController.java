package ru.practicum.mainserver.user.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.user.model.NewUserDto;
import ru.practicum.mainserver.user.service.UserService;
import ru.practicum.mainserver.user.model.User;
import ru.practicum.mainserver.user.model.UserDto;
import ru.practicum.mainserver.user.model.UserMapper;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam("ids") long[] userIds,
                                  @RequestParam(defaultValue = "0") Integer from,
                                  @RequestParam(defaultValue = "10") Integer size) {
        List<User> userList = userService.getUsers(userIds, from, size);
        return UserMapper.getUserDtoList(userList);
    }

    @PostMapping
    public UserDto addUser(@RequestBody @Valid NewUserDto newUserDto) {
        User user = UserMapper.toUser(newUserDto);
        return UserMapper.toUserDto(userService.addUser(user));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
    }
}
