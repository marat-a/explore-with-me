package ru.practicum.mainserver.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainserver.common.exceptions.NotFoundException;
import ru.practicum.mainserver.user.UserRepository;
import ru.practicum.mainserver.user.model.User;
import ru.practicum.mainserver.user.model.UserMapper;
import ru.practicum.mainserver.user.service.UserService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User addUser(User user) {
        log.debug("Add new user '{}', '{}'", user.getName(), user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with userId=" + userId + " not found.")
        );
    }

    @Override
    public List<User> getUsers(long[] ids, int from, int size) {
        log.debug("Get users list");
        PageRequest pageRequest = PageRequest.of(from, size);
        Page<User> result = userRepository.findAllByIdIn(ids, pageRequest);
        return result.toList();
    }

    @Override
    public void deleteUser(long userId) {
        log.debug("Delete user, userId={}.", userId);
        userRepository.deleteById(userId);
    }

    @Override
    public boolean isUserExist(long userId) {
        log.debug("Check is user exist, userId={}.", userId);
        return userRepository.existsById(userId);
    }


}
