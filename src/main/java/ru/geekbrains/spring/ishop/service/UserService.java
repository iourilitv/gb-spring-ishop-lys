package ru.geekbrains.spring.ishop.service;

import ru.geekbrains.spring.ishop.entity.SystemUser;
import ru.geekbrains.spring.ishop.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUserName(String username);
    boolean save(SystemUser systemUser);
}
