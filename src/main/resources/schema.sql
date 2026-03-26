-- Create Book table
CREATE TABLE IF NOT EXISTS book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(19, 2) NOT NULL,
    cover_image VARCHAR(255)
);

-- Create Users table (app_users)
CREATE TABLE IF NOT EXISTS app_users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50)
);

-- Create Cart Item table
CREATE TABLE IF NOT EXISTS cart_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_id BIGINT,
    user_id BIGINT,
    quantity INT NOT NULL,
    CONSTRAINT fk_cart_book FOREIGN KEY (book_id) REFERENCES book(id),
    CONSTRAINT fk_cart_user FOREIGN KEY (user_id) REFERENCES app_users(id)
);

-- Create Orders table
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    order_date DATETIME NOT NULL,
    total_amount DECIMAL(19, 2) NOT NULL,
    CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES app_users(id)
);

-- Create Order Item table
CREATE TABLE IF NOT EXISTS order_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,
    book_id BIGINT,
    quantity INT NOT NULL,
    price_at_time_of_purchase DECIMAL(19, 2) NOT NULL,
    CONSTRAINT fk_item_order FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_item_book FOREIGN KEY (book_id) REFERENCES book(id)
);
