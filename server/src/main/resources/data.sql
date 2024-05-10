INSERT INTO category(name) VALUES ('Eurogames');
INSERT INTO category(name) VALUES ('Ameritrash');
INSERT INTO category(name) VALUES ('Familiar');

INSERT INTO author(name, nationality) VALUES ('Alan R. Moon', 'US');
INSERT INTO author(name, nationality) VALUES ('Vital Lacerda', 'PT');
INSERT INTO author(name, nationality) VALUES ('Simone Luciani', 'IT');
INSERT INTO author(name, nationality) VALUES ('Perepau Llistosella', 'ES');
INSERT INTO author(name, nationality) VALUES ('Michael Kiesling', 'DE');
INSERT INTO author(name, nationality) VALUES ('Phil Walker-Harding', 'US');

INSERT INTO game(title, age, category_id, author_id) VALUES ('On Mars', '14', 1, 2);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Aventureros al tren', '8', 3, 1);
INSERT INTO game(title, age, category_id, author_id) VALUES ('1920: Wall Street', '12', 1, 4);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Barrage', '14', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Los viajes de Marco Polo', '12', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Azul', '8', 3, 5);

INSERT INTO customer(name) VALUES ('Ramón Martínez Quijada');
INSERT INTO customer(name) VALUES ('Mª Luisa López de la Rosa');
INSERT INTO customer(name) VALUES ('Yvonne Reed');
INSERT INTO customer(name) VALUES ('Gabriella Bridgers');
INSERT INTO customer(name) VALUES ('Imane Laurent');
INSERT INTO customer(name) VALUES ('Raquel Vázquez Navarro');

INSERT INTO loan(game_id, customer_id, start_date, end_date) VALUES (1, 2, '2019-05-12', '2019-05-23');
INSERT INTO loan(game_id, customer_id, start_date, end_date) VALUES (3, 2, '2020-05-12', '2020-05-23');
INSERT INTO loan(game_id, customer_id, start_date, end_date) VALUES (1, 4, '2019-05-17', '2019-05-27');
INSERT INTO loan(game_id, customer_id, start_date, end_date) VALUES (2, 6, '2021-05-12', '2021-05-20');
INSERT INTO loan(game_id, customer_id, start_date, end_date) VALUES (5, 5, '2022-05-12', '2022-05-23');
INSERT INTO loan(game_id, customer_id, start_date, end_date) VALUES (4, 3, '2023-05-12', '2023-05-23');
INSERT INTO loan(game_id, customer_id, start_date, end_date) VALUES (6, 1, '2024-01-12', '2019-01-23');