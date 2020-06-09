package ru.geekbrains.spring.ishop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GbSpringIShopLysApplication{
    public static void main(String[] args) {
        SpringApplication.run(GbSpringIShopLysApplication.class, args);
    }
}
//Тестирование репозитория в методе loadUserByUsername()
// в классе UserService
//путем замены классов: на Entity и на SQL2o.

//*** FOR TESTING ENTITY FRAMEWORK ***
//Result: 55220 mc
//User{id=1, userName='superadmin', password='$2a$10$N.4huWiRfudeqp6xqPmCY..X0hk5Aw5J/y2OgHyPeCUnRpm07hO4S', firstName='superadmin first_name', lastName='superadmin last_name', phoneNumber='+79991234567', email='superadmin@mail.com', deliveryAddress=null, roles=[Role{id=1, name='ROLE_SUPERADMIN'description='Главный администратор интернет-магазина. Доступ ко всем разделам магазина и всем операциям'}, Role{id=2, name='ROLE_ADMIN'description='Администратор интернет-магазина. Доступ ко всем разделам магазина. Нет прав на создание и изменение администраторов'}, Role{id=3, name='ROLE_EMPLOYEE'description='Сотрудник организации. Общий уровень доступа к внутренним ресурсам интернет-магазина. Нет доступа к пользователям'}, Role{id=4, name='ROLE_MANAGER'description='Менеджер интернет-магазина. Доступ к заказам в магазине'}]}

//*** FOR TESTING SQL2O ***
//85091
//User{id=1, userName='superadmin', password='$2a$10$N.4huWiRfudeqp6xqPmCY..X0hk5Aw5J/y2OgHyPeCUnRpm07hO4S', firstName='superadmin first_name', lastName='superadmin last_name', phoneNumber='+79991234567', email='superadmin@mail.com', deliveryAddress=null, roles=[Role{id=1, name='ROLE_SUPERADMIN'description='Главный администратор интернет-магазина. Доступ ко всем разделам магазина и всем операциям'}, Role{id=2, name='ROLE_ADMIN'description='Администратор интернет-магазина. Доступ ко всем разделам магазина. Нет прав на создание и изменение администраторов'}, Role{id=3, name='ROLE_EMPLOYEE'description='Сотрудник организации. Общий уровень доступа к внутренним ресурсам интернет-магазина. Нет доступа к пользователям'}, Role{id=4, name='ROLE_MANAGER'description='Менеджер интернет-магазина. Доступ к заказам в магазине'}]}

//*** FOR TESTING SQL2O BEGIN ***
//import org.springframework.beans.factory.annotation.Autowired;
//        import org.springframework.boot.CommandLineRunner;
//        import org.springframework.boot.SpringApplication;
//        import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.sql2o.data.Table;
//import ru.geekbrains.spring.ishop.entity.User;
//import ru.geekbrains.spring.ishop.providers.interfaces.IRoleRepositorySql2o;
//import ru.geekbrains.spring.ishop.providers.interfaces.IUserRepositorySql2o;
//import ru.geekbrains.spring.ishop.services.interfaces.IUserService;
//
//@SpringBootApplication
//public class GbSpringIShopLysApplication implements CommandLineRunner {
//
//    public static void main(String[] args) {
//        SpringApplication.run(GbSpringIShopLysApplication.class, args);
//    }
//
//    //** By full Table Begin **
////    private IUserRepositorySql2o repositorySql2o;
////
////    @Autowired
////    public GbSpringIShopLysApplication(IUserRepositorySql2o repositorySql2o) {
////        this.repositorySql2o = repositorySql2o;
////    }
////
////    @Override
////    public void run(String... args) throws Exception {
////        User user = null;
////        Long start = System.currentTimeMillis();
////
////        for (int i = 0; i < 100000; i++) {
////            user = repositorySql2o.findOneByUserName("superadmin");
////        }
////
////        Long finish = System.currentTimeMillis();
////        System.out.println(finish - start);
////        //Result: 71782 mc
////        System.out.println(user);
////        //User{id=1, userName='superadmin', password='$2a$10$N.4huWiRfudeqp6xqPmCY..X0hk5Aw5J/y2OgHyPeCUnRpm07hO4S', firstName='superadmin first_name', lastName='superadmin last_name', phoneNumber='+79991234567', email='superadmin@mail.com', deliveryAddress=null, roles=[Role{id=1, name='ROLE_SUPERADMIN'description='Главный администратор интернет-магазина. Доступ ко всем разделам магазина и всем операциям'}, Role{id=2, name='ROLE_ADMIN'description='Администратор интернет-магазина. Доступ ко всем разделам магазина. Нет прав на создание и изменение администраторов'}, Role{id=3, name='ROLE_EMPLOYEE'description='Сотрудник организации. Общий уровень доступа к внутренним ресурсам интернет-магазина. Нет доступа к пользователям'}, Role{id=4, name='ROLE_MANAGER'description='Менеджер интернет-магазина. Доступ к заказам в магазине'}]}
////    }
//    //** By full Table End **
//
//    //** By separate queries Begin **
//    private final IUserService userService;
//
//    @Autowired
//    public GbSpringIShopLysApplication(IUserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        User user = null;
//        Long start = System.currentTimeMillis();
//
//        for (int i = 0; i < 100000; i++) {
//            user = userService.findByUserName("superadmin");
//        }
//
//        Long finish = System.currentTimeMillis();
//        System.out.println(finish - start);
//        //Result: 337867 mc
//        System.out.println(user);
//        //User{id=1, userName='superadmin', password='$2a$10$N.4huWiRfudeqp6xqPmCY..X0hk5Aw5J/y2OgHyPeCUnRpm07hO4S', firstName='superadmin first_name', lastName='superadmin last_name', phoneNumber='+79991234567', email='superadmin@mail.com', deliveryAddress=null, roles=[Role{id=1, name='ROLE_SUPERADMIN'description='Главный администратор интернет-магазина. Доступ ко всем разделам магазина и всем операциям'}, Role{id=2, name='ROLE_ADMIN'description='Администратор интернет-магазина. Доступ ко всем разделам магазина. Нет прав на создание и изменение администраторов'}, Role{id=3, name='ROLE_EMPLOYEE'description='Сотрудник организации. Общий уровень доступа к внутренним ресурсам интернет-магазина. Нет доступа к пользователям'}, Role{id=4, name='ROLE_MANAGER'description='Менеджер интернет-магазина. Доступ к заказам в магазине'}]}
//    }
//    //** By separate queries End **
//
//    //** Test Table Begin **
////    private IRoleRepositorySql2o repositorySql2o;
////
////    @Autowired
////    public GbSpringIShopLysApplication(IRoleRepositorySql2o repositorySql2o) {
////        this.repositorySql2o = repositorySql2o;
////    }
////
////    @Override
////    public void run(String... args) throws Exception {
////        Table table = repositorySql2o.fetchTableById((short) 1);
////        System.out.println(table.asList());
////    }
//    //** Test Table End **
//}
//*** FOR TESTING SQL2O END ***


//!!!Не выводит роли в юзере - тестирование не будет корректно!!!
//*** FOR TESTING ENTITY FRAMEWORK BEGIN ***
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import ru.geekbrains.spring.ishop.entity.User;
//import ru.geekbrains.spring.ishop.services.interfaces.IUserService;
//
//@SpringBootApplication
//public class GbSpringIShopLysApplication implements CommandLineRunner {
//
//    public static void main(String[] args) {
//        SpringApplication.run(GbSpringIShopLysApplication.class, args);
//    }
//
////    private UserRepository userRepository;
//    private IUserService userService;
//
////    @Autowired
////    public void setUserRepository(UserRepository userRepository) {
////        this.userRepository = userRepository;
////    }
//
//    @Autowired
//    public void setUserService(IUserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        User user = null;
//        Long start = System.currentTimeMillis();
//
////        for (int i = 0; i < 100000; i++) {
////            user = userRepository.findOneByUserName("superadmin");
//            user = userService.findUserByUsername("superadmin");
////        }
//
//        Long finish = System.currentTimeMillis();
//        System.out.println(finish - start);
//        //Result:  mc
//        System.out.println(user.getRoles());
//        //
//    }
//
//}
//*** FOR TESTING ENTITY FRAMEWORK END ***