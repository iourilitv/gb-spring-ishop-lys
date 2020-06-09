package ru.geekbrains.spring.ishop.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.data.Row;
import org.sql2o.data.Table;
import ru.geekbrains.spring.ishop.entity.Address;
import ru.geekbrains.spring.ishop.entity.Role;
import ru.geekbrains.spring.ishop.entity.User;
import ru.geekbrains.spring.ishop.providers.interfaces.IUserRepositorySql2o;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UserRepositorySql2o implements IUserRepositorySql2o {
    //объявляем точку подключения - провайдера подключения к БД
    private final Sql2o sql2o;
    //формируем параметризованный запрос в БД
//    private static final String SELECT_USER_QUERY =
//            "select id, username, password, first_name, last_name, phone_number, email, " +
//                    "delivery_address_id from users where username = :u_name";

//    private static final String SELECT_USER_QUERY =
//            "select id, username, password, first_name, last_name, phone_number, email, " +
//                    "delivery_address_id from users u " +
//                    "inner join users_roles r " +
//                    "on u.id = r.user_id " +
//                    "where u.username = :u_name";

    //инжектировали провайдера подключения к БД
    public UserRepositorySql2o(@Autowired Sql2o sql2o) {
        this.sql2o = sql2o;
    }

//    @Override
//    public User findOneByUserName(String userName) {
//        //в трае с ресурсами открываем подключение к БД
//        try (Connection connection = sql2o.open()) {
//            final String query =
//                    "select * from users where username = :u_name";
//            return connection
//                    //выполняем запрос в БД
//                    //задаем false у параметра returnGeneratedKeys, иначе
//                    //sql2o добавит к запросу строку returning id; , чтобы
//                    //вернуть id сущности после вставки, который можно получить
//                    // добавив в конец цепочки вызова(вместо .executeAndFetchFirst(User.class))
//                    // .executeUpdate().getKeys(Integer.class)
//                    // но здесь это не нужно, т.к. мы не вставляем
//                    .createQuery(query, false)
//                    //добавляем значение параметра в запрос
//                    .addParameter("u_name", userName)
//                    //устанавливаем схему маппинга названий (см.в User)
//                    .setColumnMappings(User.COLUMN_MAPPINGS)
//                    //выполняем запрос и получаем первый объект
//                    .executeAndFetchFirst(User.class);
//        }
//    }
    @Override
    public User findOneByUserName(String userName) {
        //в трае с ресурсами открываем подключение к БД
        try (Connection connection = sql2o.open()) {
            final String query =
                    "select * from users u " +
                            "INNER JOIN users_roles ur ON u.id = ur.user_id " +
                            "INNER JOIN roles r ON ur.role_id = r.id " +
                            "where username = :u_name";
            Table table = connection
                    //выполняем запрос в БД
                    //задаем false у параметра returnGeneratedKeys, иначе
                    //sql2o добавит к запросу строку returning id; , чтобы
                    //вернуть id сущности после вставки, который можно получить
                    // добавив в конец цепочки вызова(вместо .executeAndFetchFirst(User.class))
                    // .executeUpdate().getKeys(Integer.class)
                    // но здесь это не нужно, т.к. мы не вставляем
                    .createQuery(query, false)
                    //добавляем значение параметра в запрос
                    .addParameter("u_name", userName)
                    //устанавливаем схему маппинга названий (см.в User)
                    .setColumnMappings(User.COLUMN_MAPPINGS)
                    //выполняем запрос и получаем первый объект
                    .executeAndFetchTable();

            List<Row> rows = table.rows();
            Collection<Role> roles = new ArrayList<>();
            rows.forEach(r -> roles.add(new Role(
                        r.getObject("role_id", Short.class),
                        r.getObject("name", String.class),
                        r.getObject("description", String.class))));
            Row row = rows.get(0);
            return new User(
                    row.getObject("id", Long.class),
                    row.getObject("username", String.class),
                    row.getObject("password", String.class),
                    row.getObject("first_name", String.class),
                    row.getObject("last_name", String.class),
                    row.getObject("phone_number", String.class),
                    row.getObject("email", String.class),
                    null,
                    roles
            );
        }
    }

    @Override
    public void save(User user) {

    }

}
