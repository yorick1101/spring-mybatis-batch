

CREATE TABLE IF NOT EXISTS `ORDERS`(
    `id`           VARCHAR(100) PRIMARY KEY,
    `customer_id`  VARCHAR(100) NOT NULL,
    `product`      VARCHAR(100) NOT NULL,
    `buy_sell`     VARCHAR(4) NOT NULL,
    `price`       DOUBLE NOT NULL,
    `amount`      DOUBLE NOT NULL,
    `status`      VARCHAR(100) NOT NULL,
    `open_timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `update_timestamp` TIMESTAMP NULL DEFAULT NULL
);

