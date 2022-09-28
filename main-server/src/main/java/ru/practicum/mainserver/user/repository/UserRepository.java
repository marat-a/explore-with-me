package ru.practicum.mainserver.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainserver.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}