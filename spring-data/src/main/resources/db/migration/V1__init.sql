BEGIN;

DROP TABLE IF EXISTS books CASCADE;
CREATE TABLE books
(
    id           BIGSERIAL PRIMARY KEY,
    title        VARCHAR(255),
    description  VARCHAR(5000),
    genre        VARCHAR(255),
    price        NUMERIC(8, 2),
    publish_year INTEGER
);
INSERT INTO books (title, description, genre, price, publish_year)
VALUES ('Harry Potter 1', 'Description 1', 'FANTASY', 300.0, 2000),
       ('Harry Potter 2', 'Description 2', 'FANTASY', 400.0, 2001),
       ('Harry Potter 3', 'Description 3', 'FANTASY', 500.0, 2002),
       ('Harry Potter 4', 'Description 4', 'FANTASY', 700.0, 2003),
       ('Harry Potter 5', 'Description 5', 'FANTASY', 440.0, 2004),
       ('Harry Potter 6', 'Description 6', 'FANTASY', 650.0, 2005),
       ('Harry Potter 7', 'Description 7', 'FANTASY', 200.0, 2006),
       ('LOTR 1', 'Description 8', 'FANTASY', 1200.0, 2006),
       ('LOTR 2', 'Description 9', 'FANTASY', 900.0, 2004),
       ('LOTR 3', 'Description 10', 'FANTASY', 600.0, 2001),
       ('Hobbit', 'Description 11', 'FANTASY', 500.0, 2001),
       ('Sherlock Holmes 1', 'Description 12', 'DETECTIVE', 400.0, 1980),
       ('Sherlock Holmes 2', 'Description 13', 'DETECTIVE', 550.0, 1987),
       ('Sherlock Holmes 3', 'Description 14', 'DETECTIVE', 650.0, 1990),
       ('The Notebook 1', 'Description 15', 'ROMANCE', 600.0, 2002),
       ('The Notebook 2', 'Description 16', 'ROMANCE', 550.0, 2004),
       ('The Notebook 3', 'Description 17', 'ROMANCE', 750.0, 2005),
       ('Dracula 1', 'Description 18', 'THRILLER', 1000.0, 1990),
       ('Dracula 2', 'Description 19', 'THRILLER', 1200.0, 2000),
       ('Dracula 3', 'Description 20', 'THRILLER', 1400.0, 2005);

COMMIT;