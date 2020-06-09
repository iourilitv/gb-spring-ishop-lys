package ru.geekbrains.spring.ishop.providers.interfaces;

import ru.geekbrains.spring.ishop.entity.User;

public interface IUserRepositorySql2o {
    User findOneByUserName(String userName);
    void save(User user);
}
