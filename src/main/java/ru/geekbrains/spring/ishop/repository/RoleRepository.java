package ru.geekbrains.spring.ishop.repository;

import ru.geekbrains.spring.ishop.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findOneByName(String theRoleName);
}
