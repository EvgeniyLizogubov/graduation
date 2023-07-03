-- H2
-- INSERT INTO USERS (NAME, EMAIL, PASSWORD)
-- VALUES ('User', 'user@yandex.ru', '{noop}password'),
--        ('Admin', 'admin@gmail.com', '{noop}admin'),
--        ('Guest', 'guest@gmail.com', '{noop}guest');
--
-- INSERT INTO USER_ROLE (ROLE, USER_ID)
-- VALUES ('USER', 1),
--        ('ADMIN', 2),
--        ('USER', 2);
--
-- INSERT INTO RESTAURANT (name, vote_date)
-- VALUES ('Kebab', '2024-01-30'),
--        ('McDonalds', '2024-01-31'),
--        ('KFC', '2024-01-31');
--
-- INSERT INTO DISH (name, price, restaurant_id)
-- VALUES ('Doner', 666, 2),
--        ('Pita', 300, 2);
--
-- INSERT INTO USER_RESTAURANT (USER_ID, RESTAURANT_ID)
-- VALUES (1, 1),
--        (1, 2),
--        (2, 2);

-- Postgres
INSERT INTO USERS (NAME, EMAIL, PASSWORD)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (name)
VALUES ('Kebab'),
       ('McDonalds'),
       ('KFC');

INSERT INTO DISH (name, price, vote_date, restaurant_id)
VALUES ('Doner', 666, '2024-01-30', 1),
       ('Pita', 300, '2024-01-31', 1);