ALTER TABLE `gb_spring_ishop_lys`.`users_roles`
    DROP FOREIGN KEY `fk_role_id`,
    DROP FOREIGN KEY `fk_user_id`;

drop table if exists roles;
drop table if exists users;
drop table if exists users_roles;
drop table if exists addresses;
drop table if exists deliveries;

CREATE TABLE `roles` (
                          `id` smallint NOT NULL AUTO_INCREMENT,
                          `name` varchar(255) NOT NULL,
                          `description` varchar(5000) NOT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `UK_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `addresses` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `country` varchar(50) NOT NULL,
                         `city` varchar(50) NOT NULL,
                         `address` varchar(500) NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `UK_co_ci_address` (`city`, `address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `username` varchar(255) NOT NULL,
                              `password` char(80) NOT NULL,
                              `first_name` varchar(255) NOT NULL,
                              `last_name` varchar(255) NOT NULL,
                              `phone_number` varchar(255) NOT NULL,
                              `delivery_address_id` bigint NOT NULL,
                              `email` varchar(255) NOT NULL,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `UK_username` (`username`),
                              KEY `fk_delivery_address_id_idx` (`delivery_address_id`),
                              CONSTRAINT `fk_delivery_address_id` FOREIGN KEY (`delivery_address_id`) REFERENCES `addresses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users_roles` (
                         `user_id` bigint NOT NULL,
                         `role_id` smallint NOT NULL,
                         PRIMARY KEY (`user_id`, `role_id`),
                         CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`)
                             REFERENCES `users` (`id`)
                             ON DELETE CASCADE ON UPDATE CASCADE,
                         CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`)
                             REFERENCES `roles` (`id`)
                             ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `deliveries` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `order_id` bigint NOT NULL,
                              `phone_number` varchar(255) NOT NULL,
                              `delivery_address_id` bigint NOT NULL,
                              `delivery_cost` decimal(19,2) NOT NULL,
                              `delivered_at` TIMESTAMP NOT NULL,
                              PRIMARY KEY (`id`),
                              KEY `fk_order_id01_idx` (`order_id`),
                              KEY `fk_delivery_address_id01_idx` (`delivery_address_id`),
                              CONSTRAINT `fk_delivery_address_id01` FOREIGN KEY (`delivery_address_id`) REFERENCES `addresses` (`id`),
                              CONSTRAINT `fk_order_id01` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE `orders`
    ADD COLUMN `user_id` bigint NOT NULL
        AFTER `status_id`;

ALTER TABLE `orders`
    DROP COLUMN `delivery_costs`;

ALTER TABLE `orders`
    ADD COLUMN `delivery_id` bigint NOT NULL
        AFTER `total_costs`;