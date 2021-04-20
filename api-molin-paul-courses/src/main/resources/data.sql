INSERT INTO course (id, description, price, theme, title) VALUES ('0', '', 22.5, 'phoque', 'Le phoque, un animal fascinant');
INSERT INTO episode (id, description, source, title) VALUES ('0', '', 'https://www.youtube.com/watch?v=Pk_qRlJyXrc', 'Les phoques du Groenland');
INSERT INTO episode (id, description, source, title) VALUES ('1', '', 'https://www.youtube.com/watch?v=Ir7POkXctgY', 'Phoques et otaries');
INSERT INTO course_episodes (course_id, episodes_id) VALUES ('0', '0');
INSERT INTO course_episodes (course_id, episodes_id) VALUES ('0', '1');

INSERT INTO user (id) VALUES ('0');
INSERT INTO user (id) VALUES ('1');

INSERT INTO purchase (id, date, course_id) VALUES ('0', '10-09-2020', '0');
INSERT INTO user_purchases (user_id, purchases_id) VALUES ('0', '0');

INSERT INTO view (id, date, episode_id) VALUES ('0', '10-09-2020', '0');
INSERT INTO user_views (user_id, views_id) VALUES ('0', '0');