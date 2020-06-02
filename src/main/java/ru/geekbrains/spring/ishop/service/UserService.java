package ru.geekbrains.spring.ishop.service;

import ru.geekbrains.spring.ishop.utils.SystemUser;
import ru.geekbrains.spring.ishop.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.PostConstruct;

public interface UserService extends UserDetailsService {
    @PostConstruct
    void initSuperAdmin();

    User findByUserName(String username);

    boolean save(SystemUser systemUser);

    boolean updatePassword(String userName, String newPassword);

}
