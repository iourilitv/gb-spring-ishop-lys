package ru.geekbrains.spring.ishop.entity;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    @NotNull(message = "не может быть пустым")
    private String userName;

    @Column(name = "password")
    @NotNull(message = "не может быть пустым")
    private String password;

    @Column(name = "first_name")
    @NotNull(message = "не может быть пустым")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "не может быть пустым")
    private String lastName;

    @Column(name = "phone_number")
    @NotNull(message = "не может быть пустым")
    private String phoneNumber;

    @Column(name = "email", unique = true)
    @NotNull(message = "не может быть пустым")
    private String email;

    @OneToOne
    @JoinColumn(name = "delivery_address_id")
    @Nullable
    private Address deliveryAddress;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public static final Map<String, String> COLUMN_MAPPINGS = new HashMap<>();

    static {
        COLUMN_MAPPINGS.put("id", "id");
        COLUMN_MAPPINGS.put("username", "userName");
        COLUMN_MAPPINGS.put("password", "password");
        COLUMN_MAPPINGS.put("first_name", "firstName");
        COLUMN_MAPPINGS.put("last_name", "lastName");
        COLUMN_MAPPINGS.put("phone_number", "phoneNumber");
        COLUMN_MAPPINGS.put("email", "email");
        COLUMN_MAPPINGS.put("delivery_address_id", "deliveryAddress");
    }

    public User() {
    }

    public User(String userName, String password, String firstName, String lastName,
                String phoneNumber, String email) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public User(String userName, String password, String firstName, String lastName,
                String phoneNumber, String email, Collection<Role> roles) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.roles = roles;
    }

    public User(Long id, String userName, String password, String firstName, String lastName,
                String phoneNumber, String email, Address deliveryAddress, Collection<Role> roles) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.deliveryAddress = deliveryAddress;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", deliveryAddress=" + deliveryAddress +
                ", roles=" + roles +
                '}';
    }
}
