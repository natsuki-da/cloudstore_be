CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL
);

CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        total_price DECIMAL(10,2) NOT NULL,
                        created_at DATETIME,
                        updated_at DATETIME,
                        status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
                        user_id BIGINT NOT NULL,
                        CONSTRAINT fk_orders_user
                            FOREIGN KEY (user_id)
                                REFERENCES users(id)
);

CREATE TABLE order_item (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            product_id BIGINT,
                            quantity INT NOT NULL,
                            price DECIMAL(10,2) NOT NULL,
                            order_id BIGINT,
                            CONSTRAINT fk_order_item_order
                                FOREIGN KEY (order_id)
                                    REFERENCES orders(id)
                                    ON DELETE CASCADE
);