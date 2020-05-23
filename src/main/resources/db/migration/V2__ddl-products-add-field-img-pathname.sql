ALTER TABLE `products`
    ADD COLUMN `img_pathname` VARCHAR(255) NOT NULL DEFAULT 'Add product img pathname!'
        AFTER `category_id`;
