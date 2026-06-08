UPDATE customers SET first_name = 'Jane' WHERE id = 1;

UPDATE products SET price = price * 1.1;

UPDATE orders SET status = 'SHIPPED' WHERE id = 1;

UPDATE categories SET parent_category_id = NULL WHERE id = 4;

UPDATE reviews SET rating = 4 WHERE id = 1;

UPDATE customers SET updated_at = NOW() WHERE id = 1;

UPDATE products SET is_active = FALSE WHERE price < 10;

UPDATE orders SET shipped_date = NOW() WHERE status = 'SHIPPED';

UPDATE order_items SET total_price = quantity * unit_price;

UPDATE customers SET phone = '123456789' WHERE email = 'john@example.com';
