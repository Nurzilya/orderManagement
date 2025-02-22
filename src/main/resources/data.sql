
-- Insert users
-- Default passwords here are: fun123
INSERT INTO users (username, password) VALUES
('user','{bcrypt}$2a$10$qeS0HEh7urweMojsnwNAR.vcXJeXR1UcMRZ2WcGQl9YeuspUdgF.q');
INSERT INTO users (username, password) VALUES
('admin','{bcrypt}$2a$10$qeS0HEh7urweMojsnwNAR.vcXJeXR1UcMRZ2WcGQl9YeuspUdgF.q');

-- Insert roles
INSERT INTO user_roles (user_id, role) VALUES
(1,'ROLE_USER');
INSERT INTO user_roles (user_id, role) VALUES
(2,'ROLE_ADMIN');

ALTER TABLE orders ALTER COLUMN id RESTART WITH 1;

-- Insert Categories
INSERT INTO categories (name) VALUES ('Drinks');
INSERT INTO categories (name) VALUES ('Sandwiches');
INSERT INTO categories (name, parent_category_id) VALUES ('Juices', 1);
INSERT INTO categories (name, parent_category_id) VALUES ('Vine', 1);

-- Insert Products
INSERT INTO products (name, price, stock_quantity, category_id, image) VALUES ('White vine', 1.00, 3, 3, 'whiteVine.jpg');
INSERT INTO products (name, price, stock_quantity, category_id, image) VALUES ('Orange juice', 1.00, 2, 1, 'orangeJuice.jpg');
INSERT INTO products (name, price, stock_quantity, category_id, image) VALUES ('Apple juice', 1.50, 1, 1, 'appleJuice.jpg');

-- Insert Orders
INSERT INTO orders (status, email, seat_letter, seat_number) VALUES ('OPEN', 'customer@example.com', 'A', 10);

-- Insert PaymentDetails
INSERT INTO payment_details (card_token, payment_status, payment_date, payment_gateway) VALUES ('1234-5678-9012-3456', 'PAID', NOW(), 'Stripe');

-- Insert Order-Product Relationship
INSERT INTO order_products (order_id, product_id) VALUES (1, 1);
INSERT INTO order_products (order_id, product_id) VALUES (1, 2);