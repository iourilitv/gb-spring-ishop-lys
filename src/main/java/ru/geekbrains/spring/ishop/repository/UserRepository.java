package ru.geekbrains.spring.ishop.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.geekbrains.spring.ishop.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends
        CrudRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findOneByUserName(String userName);

}
