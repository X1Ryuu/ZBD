DELETE FROM customers WHERE id = 10;

DELETE FROM orders WHERE status = 'CANCELLED';

DELETE FROM products WHERE is_active = FALSE;

DELETE FROM reviews WHERE rating <= 2;

DELETE FROM categories WHERE id = 5;

DELETE FROM order_items WHERE order_id = 1;

DELETE FROM customers WHERE email LIKE '%spam%';

DELETE FROM orders WHERE created_at < NOW() - INTERVAL '1 year';

DELETE FROM products WHERE price > 10000;

DELETE FROM reviews WHERE created_at < NOW() - INTERVAL '6 months';
