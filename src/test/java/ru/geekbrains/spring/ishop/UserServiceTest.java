package ru.geekbrains.spring.ishop;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.geekbrains.spring.ishop.entity.User;
import ru.geekbrains.spring.ishop.repository.UserRepository;
import ru.geekbrains.spring.ishop.service.UserService;
import ru.geekbrains.spring.ishop.service.UserServiceImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

//    @Autowired
//    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void init() {
//        userService = new UserServiceImpl();
    }

    @Test
    @Ignore
    public void getUser() {
        Assert.assertEquals("superadmin", userService.findById(1L).getUserName());
    }

    @Test
    public void findByNameTest () {
//        this.entityManager.persist(new User("user1", "user1@mail.ru"));
        User user = this.userRepository.findOneByUserName("superadmin");
        Assert.assertEquals("+79991234567", user.getPhoneNumber());
        Assert.assertEquals("superadmin@mail.com", user.getEmail());
    }
}