package ru.practicum.mainserver.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.mainserver.common.exceptions.NotFoundException;
import ru.practicum.mainserver.user.UserRepository;
import ru.practicum.mainserver.user.model.User;
import ru.practicum.mainserver.user.model.UserDto;
import ru.practicum.mainserver.user.model.UserMapper;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto addUser(User user) {
        user = userRepository.save(user);
        log.info("Add new user '{}', '{}'", user.getName(), user.getEmail());
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto getUser(Long userId) {
        log.info("Get users with userId=" + userId);
        return UserMapper.toUserDto(findUserById(userId));
    }

    @Override
    public User findUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with userId=" + userId + " not found.")
        );
        log.info("Get users with userId=" + userId);
        return user;
    }

    @Override
    public List<UserDto> getUsers(long[] ids, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from, size);
        Page<User> result;
        if (ids != null) {
            result = userRepository.findAllByIdIn(ids, pageRequest);
        } else result = userRepository.findAll(pageRequest);
        List<User> users = result.toList();
        log.info("Get user's list with parameters: from =" + from + ", size=" + size + ".");
        return UserMapper.toUserDtoList(users);
    }

    @Override
    public void deleteUser(long userId) {
        if (isUserExist(userId)) {
            userRepository.deleteById(userId);
            log.info("Delete user, userId={}.", userId);
        } else throw new NotFoundException("User with userId=" + userId + " not found.");
    }

    @Override
    public boolean isUserExist(long userId) {
        boolean isUserExist = userRepository.existsById(userId);
        log.info("Check is user exist, userId={}, result = {}", userId, isUserExist);
        return isUserExist;
    }

}
