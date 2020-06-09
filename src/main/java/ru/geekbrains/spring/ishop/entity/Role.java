package ru.geekbrains.spring.ishop.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Short id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    public static final Map<String, String> COLUMN_MAPPINGS = new HashMap<>();

    static {
        COLUMN_MAPPINGS.put("user_id", "id");
        COLUMN_MAPPINGS.put("role_id", "id");
//        COLUMN_MAPPINGS.put("name", "name");
//        COLUMN_MAPPINGS.put("description", "description");
    }

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Role(Short id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Role{" + "id=" + id + ", " +
                "name='" + name + '\'' +
                "description='" + description + '\'' +
                '}';
    }
}
