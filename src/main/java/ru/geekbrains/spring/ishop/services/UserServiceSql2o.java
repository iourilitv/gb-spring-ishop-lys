package ru.geekbrains.spring.ishop.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.spring.ishop.entity.Role;
import ru.geekbrains.spring.ishop.providers.interfaces.IRoleRepositorySql2o;
import ru.geekbrains.spring.ishop.providers.interfaces.IUserRepositorySql2o;
import ru.geekbrains.spring.ishop.services.interfaces.IUserServiceSql2o;
import ru.geekbrains.spring.ishop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.ishop.utils.SystemUser;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceSql2o implements IUserServiceSql2o {
    //TODO
    Logger logger = LoggerFactory.getLogger(UserServiceSql2o.class);

    private BCryptPasswordEncoder passwordEncoder;
    //указываем интерфейс, вместо его реализации для слабой связанности.
    //Это позволяет здесь применять разные реализации не переписывая здесь код
    private IUserRepositorySql2o userRepositorySql2o;
    private IRoleRepositorySql2o roleRepositorySql2o;

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRepositorySql2o(IUserRepositorySql2o userRepositorySql2o) {
        this.userRepositorySql2o = userRepositorySql2o;
    }

    @Autowired
    public void setRoleRepositorySql2o(IRoleRepositorySql2o roleRepositorySql2o) {
        this.roleRepositorySql2o = roleRepositorySql2o;
    }

    //TODO переделать: вводить первого юзера
//    @Override
//    @Transactional
//    @PostConstruct
//    public void initSuperAdmin() {
//        if (findByUserName("superadmin") != null) {
//            return;
//        }
//        User user = new User("superadmin", passwordEncoder.encode("superadmin"),
//                "superadmin first_name", "superadmin last_name",
//                "+79991234567", "superadmin@mail.com");
//        user.setRoles(roleRepositorySql2o.findAll());
//        userRepositorySql2o.save(user);
//    }

    //**** Способ с одним запросом в бд за сводной таблицей со всем что нужно ****
    @Override
    @Transactional
    public User findByUserName(String username) {
        return userRepositorySql2o.findOneByUserName(username);
    }
    //**** Способ с отдельными запросами в бд за каждой сущностью ****
//    @Override
//    @Transactional
//    public User findByUserName(String username) {
//        User user = userRepositorySql2o.findOneByUserName(username);
//        Collection<Role> roles = roleRepositorySql2o.fetchRolesByUser(user);
//        user.setRoles(roles);
//        return user;
//    }

    @Override
    @Transactional
    public boolean save(SystemUser systemUser) {
//        User user = new User();
//
//        if (findByUserName(systemUser.getUserName()) != null) {
//            return false;
//        }
//
//        user.setUserName(systemUser.getUserName());
//        user.setPassword(passwordEncoder.encode(systemUser.getPassword()));
//        user.setFirstName(systemUser.getFirstName());
//        user.setLastName(systemUser.getLastName());
//        user.setPhoneNumber(systemUser.getPhoneNumber());
//        user.setEmail(systemUser.getEmail());
//        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public boolean updatePassword(String userName, String newPassword) {
//        User user = userRepository.findOneByUserName(userName);
//        if(user == null) {
//            return false;
//        }
//        user.setPassword(passwordEncoder.encode(newPassword));
//        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //TODO
        logger.info("*************************** loadUserByUsername BEGIN TEST *************************");
        Long start = System.currentTimeMillis();
        User user = null;
//        for (int i = 0; i < 100000; i++) {
            user = userRepositorySql2o.findOneByUserName(userName);
//        }
        Long finish = System.currentTimeMillis();
        long time = finish - start;
        logger.info("Execution time: " + time);
        logger.info("User: " + user);
        logger.info("*************************** loadUserByUsername END TEST *************************");
        //2020-06-09 14:28:55.002  INFO 14976 --- [nio-8080-exec-4] r.g.s.ishop.services.UserServiceSql2o    : Execution time: 86248
        //2020-06-09 14:28:55.002  INFO 14976 --- [nio-8080-exec-4] r.g.s.ishop.services.UserServiceSql2o    : User: User{id=1, userName='superadmin', password='$2a$10$N.4huWiRfudeqp6xqPmCY..X0hk5Aw5J/y2OgHyPeCUnRpm07hO4S', firstName='superadmin first_name', lastName='superadmin last_name', phoneNumber='+79991234567', email='superadmin@mail.com', deliveryAddress=null, roles=[Role{id=1, name='ROLE_SUPERADMIN'description='Главный администратор интернет-магазина. Доступ ко всем разделам магазина и всем операциям'}, Role{id=2, name='ROLE_ADMIN'description='Администратор интернет-магазина. Доступ ко всем разделам магазина. Нет прав на создание и изменение администраторов'}, Role{id=3, name='ROLE_EMPLOYEE'description='Сотрудник организации. Общий уровень доступа к внутренним ресурсам интернет-магазина. Нет доступа к пользователям'}, Role{id=4, name='ROLE_MANAGER'description='Менеджер интернет-магазина. Доступ к заказам в магазине'}]}

//        User user = userRepositorySql2o.findOneByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}

