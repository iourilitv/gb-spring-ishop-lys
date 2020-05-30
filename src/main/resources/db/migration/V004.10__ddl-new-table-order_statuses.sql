drop table if exists order_statuses cascade;

CREATE TABLE `order_statuses` (
                              `id` smallint NOT NULL AUTO_INCREMENT,
                              `title` varchar(255) NOT NULL,
                              `description` text,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `title_UNIQUE` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
    COMMENT='SMALLINT == short';