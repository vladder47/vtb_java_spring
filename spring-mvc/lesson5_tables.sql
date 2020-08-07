BEGIN;

DROP TABLE IF EXISTS clients CASCADE;
CREATE TABLE clients
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    age  INTEGER
);
INSERT INTO clients (name, age)
VALUES ('Vladislav', 22),
       ('Boris', 25),
       ('Denis', 30),
       ('Vasiliy', 21),
       ('Andrey', 18);

DROP TABLE IF EXISTS products CASCADE;
CREATE TABLE products
(
    id    BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    price INTEGER
);
INSERT INTO products (title, price)
VALUES ('phone', 1000),
       ('notebook', 3500),
       ('fridge', 8000),
       ('microwave', 2500),
       ('tablet', 1200);

COMMIT;