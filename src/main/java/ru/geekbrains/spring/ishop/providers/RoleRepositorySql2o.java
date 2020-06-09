package ru.geekbrains.spring.ishop.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.data.Table;
import ru.geekbrains.spring.ishop.entity.Role;
import ru.geekbrains.spring.ishop.entity.User;
import ru.geekbrains.spring.ishop.providers.interfaces.IRoleRepositorySql2o;

import java.util.Collection;

@Component
public class RoleRepositorySql2o implements IRoleRepositorySql2o {
    private final Sql2o sql2o;

    public RoleRepositorySql2o(@Autowired Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Role getRoleById(Short id) {
        try (Connection connection = sql2o.open()) {
            final String query =
                    "select * from roles where id = :r_id";
            return connection
                    .createQuery(query, false)
                    .addParameter("r_id", id)
                    //выполняем запрос и получаем первый объект
                    .executeAndFetchFirst(Role.class);
        }
    }

    @Override
    public Collection<Role> fetchRolesByUser(User user) {
        try (Connection connection = sql2o.open()) {
            final String query =
                    "select * from users_roles r " +
                            "where r.user_id = :u_id";
            Collection<Role> roles  = connection
                    .createQuery(query, false)
                    .addParameter("u_id", user.getId())
                    .setColumnMappings(Role.COLUMN_MAPPINGS)
                    .executeAndFetch(Role.class);
            roles.forEach(r -> {
                Role role = getRoleById(r.getId());
                r.setName(role.getName());
                r.setDescription(role.getDescription());
            });
            return roles;
        }
    }

    @Override
    public Collection<Role> findAll() {
        return null;
    }

    @Override
    public Table fetchTableById(Short id) {
        try (Connection connection = sql2o.open()) {
            final String query =
                    "select * from users_roles ur inner join roles r " +
                            "on ur.role_id = r.id " +
                            "where ur.user_id = :u_id";

            Table table = connection.createQuery(query, false)
                    .addParameter("u_id", id)
                    .executeAndFetchTable();

//            return connection
//                    .createQuery(query, false)
//                    .addParameter("r_id", id)
//                    //выполняем запрос и получаем первый объект
//                    .executeAndFetchFirst(Role.class);
            return table;
        }
    }
}