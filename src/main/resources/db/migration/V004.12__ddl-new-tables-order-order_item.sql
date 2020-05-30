drop table if exists orders cascade;

CREATE TABLE `orders` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `status_id` smallint NOT NULL,
                          `total_items_costs` decimal(19,2) NOT NULL,
                          `delivery_costs` decimal(19,2) NOT NULL,
                          `total_costs` decimal(19,2) NOT NULL,
                          `created_at` TIMESTAMP NOT NULL DEFAULT NOW(),
                          `updated_at` TIMESTAMP NOT NULL DEFAULT NOW(),
                          PRIMARY KEY (`id`),
                          KEY `FK_STATUS_ID` (`status_id`),
                          CONSTRAINT `FK_STATUS_ID` FOREIGN KEY (`status_id`) REFERENCES `order_statuses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

drop table if exists order_item cascade;

CREATE TABLE `order_item` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `order_id` bigint NOT NULL,
                              `product_id` bigint NOT NULL,
                              `item_price` decimal(19,2) NOT NULL,
                              `quantity` int NOT NULL,
                              `item_costs` decimal(19,2) NOT NULL,
                              PRIMARY KEY (`id`),
                              KEY `FK_ORDER_ID` (`order_id`),
                              KEY `FK_PRODUCT_ID_ORD_IT` (`product_id`),
                              CONSTRAINT `FK_PRODUCT_ID_ORD_IT` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
                              CONSTRAINT `FK_ORDER_ID` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;