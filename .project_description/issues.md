# Issues.

##1. flyway при запуске миграции данные добавляет, но выдает предупреждение:
[WARNING] DB: Unknown table 'gb_spring_ishop_lys.products' (SQL State: 42S02 - Error Code: 1051);
Solution: just ignore(lys)
Conclusion: Похоже flyway ругается на строку drop table products cascade;
Если запустить еще раз миграцию(без очистки) то все работает - 
[INFO] Schema `gb_spring_ishop_lys` is up to date. No migration necessary.

##2. Thymeleaf + Spring.
 Не смог реализовать совместную фильтрацию по ссылкам на категории в header.html 
 и в фильтре. Все работает отдельно. Либо ссылки, либо фильтр. При применении фильтра слетают ссылки и наоборот. 
 После добавления параметра category в filterDef, появляется дублирование этого параметра.
 Возможное решение: 
 в файле header.html в ссылке в элементе меню удалить этот параметр из filterDef 
 и добавить новый параметр.
 Код не работает:
                             <!-- does not work -->
 <!--                            <li th:class="${#strings.equals(param.category, category.id) ? 'nav-item nav-pills' : 'nav-item'}">-->
 <!--                                <a th:class="${#strings.equals(param.category, category.id) ? 'nav-link active color-white' : 'nav-link active'}"-->
 <!--                                   th:href="@{'/catalog/all' + ${__${#strings.replace(${filterDef},-->
 <!--                                   'category=' + ${param.category}, 'category=' + ${category.id})}__}}"-->
 <!--                                   th:text="${category.title}"></a>-->
 <!--                            </li>-->
 Нужно, либо найти синтаксис препроцессора thymeleaf, либо делать это на стороне StringBuilderа, 
 либо создать свой класс filterDef вместо StringBuilder.
 В своем классе можно реализовать метод замены значения параметра.
 А вообще лучше все фильтры делать в одном месте. 
 Пока удалил ссылки категорий из header, и добавил категории в фильтр каталога.
  
##3. CSS-styles does not change.
Не могу поменять стили у кнопок в форме фильтра.
Почему-то стили у button в style.css перебивают мои в my-changes.css, хотя ссылка 
на мою таблицу стилей стоит и позже. Странно!?

##4. Не работает ajax.
Страница все равно обновляется при нажатии на кнопку Add To Cart в каталоге
куда бы не добавить в catalog.html строку
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>.

##5. Не выводит roles у объекта User, добавленного как атрибут в сессию.
В классе CustomAuthenticationSuccessHandler в методе onAuthenticationSuccess() получаем из БД 
объект User theUser = userService.findByUserName(userName);.
Добавляем его в сессию (точно добавляется, проверил).
(Внимание! Над onAuthenticationSuccess() должна быть аннотация @Transactional,
иначе роли не подгружаются.)
Но в классе OrderController в методе allOrders() при попытке получить объект User 
из сессии роли не подгружаются.
User user = (User)session.getAttribute("user");
System.out.println(user);
//org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: ru.geekbrains.spring.ishop.entity.User.roles, could not initialize proxy - no Session
Временное решение.
Пришлось в сессию добавить атрибут с коллекцией ролей
session.setAttribute("userRoles", theUser.getRoles());.
И на приемной стороне получить его из сессии и добавить в User.
Collection<Role> roles = (Collection<Role>) session.getAttribute("userRoles");
user.setRoles(roles);
System.out.println(user);
Тогда объект User выводится полностью.
//User{id=1, userName='superadmin', password='$2a$10$N.4huWiRfudeqp6xqPmCY..X0hk5Aw5J/y2OgHyPeCUnRpm07hO4S', firstName='superadmin first_name', lastName='superadmin last_name', phoneNumber='+79991234567', email='superadmin@mail.com', deliveryAddress=null, roles=[Role{id=1, name='ROLE_SUPERADMIN'description='Главный администратор интернет-магазина. Доступ ко всем разделам магазина и всем операциям'}, Role{id=2, name='ROLE_ADMIN'description='Администратор интернет-магазина. Доступ ко всем разделам магазина. Нет прав на создание и изменение администраторов'}, Role{id=3, name='ROLE_EMPLOYEE'description='Сотрудник организации. Общий уровень доступа к внутренним ресурсам интернет-магазина. Нет доступа к пользователям'}, Role{id=4, name='ROLE_MANAGER'description='Менеджер интернет-магазина. Доступ к заказам в магазине'}]}
Не помогло выделение этого кода в отдельный метод с аннотацией @Transactional