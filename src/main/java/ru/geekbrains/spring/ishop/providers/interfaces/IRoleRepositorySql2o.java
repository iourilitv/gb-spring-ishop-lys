package ru.geekbrains.spring.ishop.providers.interfaces;

import ru.geekbrains.spring.ishop.entity.Role;
import ru.geekbrains.spring.ishop.entity.User;

import java.util.Collection;

public interface IRoleRepositorySql2o {
    Role getRoleById(Short id);
    Collection<Role> fetchRolesByUser(User user);
    Collection<Role> findAll();
}
