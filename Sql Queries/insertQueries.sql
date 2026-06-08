INSERT INTO categories (name, description, created_at)
VALUES ('Electronics', 'Devices', NOW());

INSERT INTO categories (name, parent_category_id, created_at)
VALUES ('Phones', 1, NOW());

INSERT INTO customers (email, first_name, last_name, created_at, updated_at)
VALUES ('john2@example.com', 'John', 'Doe', NOW(), NOW());

INSERT INTO products (name, price, sku, is_active, created_at, updated_at)
VALUES ('iPhone', 999.99, 'IP-NEW-001', TRUE, NOW(), NOW());

INSERT INTO orders (status, total_amount, customer_id, created_at, updated_at)
VALUES ('PENDING', 100.00, 1, NOW(), NOW());

INSERT INTO order_items (quantity, unit_price, total_price, order_id, product_id)
VALUES (2, 50.00, 100.00, 1, 1);

INSERT INTO reviews (rating, title, product_id, customer_id, created_at)
VALUES (5, 'Great!', 1, 1, NOW());

INSERT INTO customers (email, created_at, updated_at)
VALUES ('unique2@test.com', NOW(), NOW());

INSERT INTO products (name, sku, price, category_id, is_active, created_at, updated_at)
VALUES ('Laptop', 'LP-NEW-001', 1500.00, 1, TRUE, NOW(), NOW());

INSERT INTO orders (status, subtotal, tax_amount, total_amount, created_at, updated_at)
VALUES ('NEW', 80.00, 20.00, 100.00, NOW(), NOW());
