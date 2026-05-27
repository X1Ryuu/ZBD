SELECT o.id, c.email
FROM orders o
JOIN customers c ON o.customer_id = c.id;

SELECT oi.*, p.name AS product_name
FROM order_items oi
JOIN products p ON oi.product_id = p.id;

SELECT r.rating, p.name AS product_name
FROM reviews r
JOIN products p ON r.product_id = p.id;

SELECT r.*, c.email
FROM reviews r
JOIN customers c ON r.customer_id = c.id;

SELECT o.id AS order_id, SUM(oi.total_price) AS items_total
FROM orders o
JOIN order_items oi ON o.id = oi.order_id
GROUP BY o.id;

SELECT p.name AS product_name, c.name AS category_name
FROM products p
JOIN categories c ON p.category_id = c.id;

SELECT c1.name AS category_name, c2.name AS parent_name
FROM categories c1
LEFT JOIN categories c2 ON c1.parent_category_id = c2.id;

SELECT o.id AS order_id, p.name AS product_name, oi.quantity
FROM orders o
JOIN order_items oi ON o.id = oi.order_id
JOIN products p ON oi.product_id = p.id;

SELECT c.email, COUNT(o.id) AS total_orders
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id
GROUP BY c.email;

SELECT p.name AS product_name, AVG(r.rating) AS avg_rating
FROM products p
LEFT JOIN reviews r ON p.id = r.product_id
GROUP BY p.name;