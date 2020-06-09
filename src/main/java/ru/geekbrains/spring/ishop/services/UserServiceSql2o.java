package ru.geekbrains.spring.ishop.services;

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
//    private UserRepository userRepository;
//    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    //указываем интерфейс, вместо его реализации для слабой связанности.
    //Это позволяет здесь применять разные реализации не переписывая здесь код
    private IUserRepositorySql2o userRepositorySql2o;
    private IRoleRepositorySql2o roleRepositorySql2o;

//    @Autowired
//    public void setUserRepository(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

//    @Autowired
//    public void setRoleRepository(RoleRepository roleRepository) {
//        this.roleRepository = roleRepository;
//    }

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
//        user.setRoles((Collection<Role>) roleRepository.findAll());
//        userRepository.save(user);
//    }
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

//    @Override
//    @Transactional
//    public User findByUserName(String username) {
//        return userRepository.findOneByUserName(username);
//    }
    @Override
    @Transactional
    public User findByUserName(String username) {
        return userRepositorySql2o.findOneByUserName(username);
    }
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

//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        User user = userRepository.findOneByUserName(userName);
//        if (user == null) {
//            throw new UsernameNotFoundException("Invalid username or password.");
//        }
//        return new org.springframework.security.core.userdetails.User(
//                user.getUserName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
//    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //TODO
        System.out.println("*************************** loadUserByUsername *************************");
        Long start = System.currentTimeMillis();
        User user = null;
        for (int i = 0; i < 100000; i++) {
            user = userRepositorySql2o.findOneByUserName(userName);
        }
        Long finish = System.currentTimeMillis();
        System.out.println(finish - start);
        System.out.println(user);

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

