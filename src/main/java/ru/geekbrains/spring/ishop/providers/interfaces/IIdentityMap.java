package ru.geekbrains.spring.ishop.providers.interfaces;

public interface IIdentityMap {
    void add(Object object);

    Object delete(Object object);

    Object get(Object object);

}
