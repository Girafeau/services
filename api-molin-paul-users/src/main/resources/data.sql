INSERT INTO user (id, email, firstname, lastname, status) VALUES ('0', 'jean.long@test.com', 'jean', 'long', 'unverified');
INSERT INTO user (id, email, firstname, lastname, status) VALUES ('1', 'anne.long@test.com', 'anne', 'long', 'unverified');
INSERT INTO card (id, number, code, owner, date, amount) VALUES ('0', 523545, 232, 'jean long', '10-09-2023', 200);
INSERT INTO user_cards (user_id, cards_id) VALUES ('1', '0');