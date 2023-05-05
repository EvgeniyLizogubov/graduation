INSERT INTO USERS (NAME, EMAIL, PASSWORD, RESTAURANT_ID)
VALUES ('User', 'user@yandex.ru', '{noop}password', null),
       ('Admin', 'admin@gmail.com', '{noop}admin', null),
       ('Guest', 'guest@gmail.com', '{noop}guest', null);

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (name, date)
VALUES ('Kebab', '2020-01-30'),
       ('McDonalds', '2020-01-30');

INSERT INTO DISH (name, price, restaurant_id)
VALUES ('Doner', 666, 1),
       ('Pita', 300, 1);

INSERT INTO MEAL (date_time, description, calories, user_id)
VALUES ('2020-01-30 10:00:00', 'Завтрак', 500, 1),
       ('2020-01-30 13:00:00', 'Обед', 1000, 1),
       ('2020-01-30 20:00:00', 'Ужин', 500, 1),
       ('2020-01-31 0:00:00', 'Еда на граничное значение', 100, 1),
       ('2020-01-31 10:00:00', 'Завтрак', 500, 1),
       ('2020-01-31 13:00:00', 'Обед', 1000, 1),
       ('2020-01-31 20:00:00', 'Ужин', 510, 1),
       ('2020-01-31 14:00:00', 'Админ ланч', 510, 2),
       ('2020-01-31 21:00:00', 'Админ ужин', 1500, 2);