package ru.practicum.mainserver.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainserver.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAllByIdIn(long[] ids, Pageable pageable);
}