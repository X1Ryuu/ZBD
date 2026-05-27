


SELECT COUNT(*) FROM customers;

SELECT SUM(total_amount) FROM orders;


SELECT AVG(rating) FROM reviews;

SELECT MAX(price) FROM products;

SELECT MIN(price) FROM products;

SELECT customer_id, COUNT(*) AS order_count
FROM orders
GROUP BY customer_id;

SELECT product_id, SUM(quantity) AS total_sold
FROM order_items
GROUP BY product_id;

SELECT status, COUNT(*) AS status_count
FROM orders
GROUP BY status;

SELECT category_id, AVG(price) AS avg_price
FROM products
GROUP BY category_id;

SELECT order_date::date AS order_day, COUNT(*) AS orders_placed
FROM orders
GROUP BY order_date::date
ORDER BY order_day;
