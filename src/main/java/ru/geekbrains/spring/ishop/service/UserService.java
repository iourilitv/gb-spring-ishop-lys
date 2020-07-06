package ru.geekbrains.spring.ishop.service;

import org.springframework.data.domain.Page;
import ru.geekbrains.spring.ishop.entity.Role;
import ru.geekbrains.spring.ishop.utils.SystemUser;
import ru.geekbrains.spring.ishop.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.geekbrains.spring.ishop.utils.filters.UserFilter;

import javax.annotation.PostConstruct;
import java.util.List;

public interface UserService extends UserDetailsService {
    @PostConstruct
    void initSuperAdmin();

    Page<User> findAll(UserFilter userFilter, String id);

    User findById(Long user_id);

    User findByUserName(String username);

    boolean save(SystemUser systemUser);

    void updatePassword(String userName, String newPassword);

    void delete(User user);

    List<Role> getAllRoles();

}
