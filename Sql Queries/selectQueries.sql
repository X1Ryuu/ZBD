SELECT * FROM customers;

SELECT id, email FROM customers WHERE email LIKE '%@gmail.com';

SELECT * FROM products WHERE price > 100 ORDER BY price DESC;

SELECT name, description FROM categories WHERE parent_category_id IS NULL;

SELECT * FROM orders WHERE status = 'SHIPPED';

SELECT * FROM reviews WHERE rating >= 4;

SELECT COUNT(*) FROM orders;

SELECT AVG(price) FROM products;

SELECT * FROM customers WHERE created_at > NOW() - INTERVAL '30 days';

SELECT DISTINCT brand FROM products;
