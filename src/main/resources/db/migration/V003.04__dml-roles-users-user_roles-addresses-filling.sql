SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `roles` (`name`, `description`) VALUES
('ROLE_EMPLOYEE', 'Сотрудник организации. Общий доступ к ресурсам компании'),
('ROLE_MANAGER', 'Менеджер интернет-магазина. Доступ к разделам магазина'),
('ROLE_ADMIN', 'Администратор интернет-магазина. Доступ ко всем разделам магазина');

INSERT INTO `users` (`username`,`password`,`first_name`,`last_name`,`phone_number`,`email`)
VALUES
('admin', '$2a$10$4p6U8Ve1ZjJ/S0Qd9RFyB.hJjpusgdYmTtIIqpHs3k0hfbhDe6cyq', 'Admin', 'Admin','+79881111111', 'admin@gmail.com');

INSERT INTO `users_roles` (`user_id`, `role_id`)
VALUES
(1, 1),
(1, 2),
(1, 3);

INSERT INTO `addresses` (`country`, `city`, `address`)
VALUES
('USA', 'New York', '18a Diagon Alley'),
('RF', 'Moscow', '12 Lenina');