
INSERT INTO USERS(USERNAME, PASSWORD, EMAIL) VALUES ('tflucker', '$2a$10$DcS8onszYXA5wB3TbImi5.YwLykEn2A1hGrI5igXoLagDth61nhbS', 'tflucker@email.com');
INSERT INTO USERS(USERNAME, PASSWORD, EMAIL) VALUES ('jDorancy', '$2a$10$t2Zx/eVov1DGksmJiKskh.bqYHcE6fRclUN2auGMKoehT8Rmmhi0K', 'jDorancy@email.com');
INSERT INTO USERS(USERNAME, PASSWORD, EMAIL) VALUES ('xHou', '$2a$10$pal/qdIdiU0b9bY42itNa.GXXf.cWqrf52Jt94rjFw6XjC4dmR5j2', 'xHou@email.com');
INSERT INTO USERS(USERNAME, PASSWORD, EMAIL) VALUES ('wLiang', '$2a$10$G898v2XHI8S21YWfvGHUBuU.JyhH1a9ondWjJoCd0qe3hYjKfz7Wy', 'wLiang@email.com');

INSERT INTO BANK_ACCOUNT(USER_ID, ACCOUNT_TYPE, ACCOUNT_DESCRIPTION, BALANCE, STATUS) VALUES (1, 'SAVING', 'Honeymoon Fund', 13291.88, 'ACTIVE');
INSERT INTO BANK_ACCOUNT(USER_ID, ACCOUNT_TYPE, ACCOUNT_DESCRIPTION, BALANCE, STATUS) VALUES (1, 'SAVING', 'Honeymoon', 200.88, 'ACTIVE');
INSERT INTO BANK_ACCOUNT(USER_ID, ACCOUNT_TYPE, ACCOUNT_DESCRIPTION, BALANCE, STATUS) VALUES (1, 'SAVING', 'Honeymoon', 500.88, 'ACTIVE');
INSERT INTO BANK_ACCOUNT(USER_ID, ACCOUNT_TYPE, ACCOUNT_DESCRIPTION, BALANCE, STATUS) VALUES (2, 'CHECKING', 'Personal Checking Account', 5810.23, 'ACTIVE');
INSERT INTO BANK_ACCOUNT(USER_ID, ACCOUNT_TYPE, ACCOUNT_DESCRIPTION, BALANCE, STATUS) VALUES (3, 'CHECKING', 'Educational Stipend Account', 928.22, 'ACTIVE');
INSERT INTO BANK_ACCOUNT(USER_ID, ACCOUNT_TYPE, ACCOUNT_DESCRIPTION, BALANCE, STATUS) VALUES (4, 'SAVING', 'Gaming PC fund', 800.00, 'ACTIVE');

INSERT INTO TRANSACTION(BANK_ACCOUNT_ID, MONEY, TIME, COUNTERPARTY, TRANSACTION_DESCRIPTION,STATUS) VALUES (1, 10.0, '4/20/2022', 'Tim', 'transfer','VALID');
INSERT INTO TRANSACTION(BANK_ACCOUNT_ID, MONEY, TIME, COUNTERPARTY, TRANSACTION_DESCRIPTION,STATUS) VALUES (1, 20.0, '5/20/2022', 'Tim-1', 'transfer','VALID');
INSERT INTO TRANSACTION(BANK_ACCOUNT_ID, MONEY, TIME, COUNTERPARTY, TRANSACTION_DESCRIPTION,STATUS) VALUES (2, 20.0, '3/22/2022', 'jDorancy', 'transfer','VALID');
INSERT INTO TRANSACTION(BANK_ACCOUNT_ID, MONEY, TIME, COUNTERPARTY, TRANSACTION_DESCRIPTION,STATUS) VALUES (1, 200.0, '5/20/2022', 'Tim-1', 'transfer','VALID');
INSERT INTO TRANSACTION(BANK_ACCOUNT_ID, MONEY, TIME, COUNTERPARTY, TRANSACTION_DESCRIPTION,STATUS) VALUES (3, 30.0, '5/20/2022', 'xHou', 'transfer','VALID');
INSERT INTO TRANSACTION(BANK_ACCOUNT_ID, MONEY, TIME, COUNTERPARTY, TRANSACTION_DESCRIPTION,STATUS) VALUES (4, 40.0, '5/23/2022', 'wLiang', 'transfer','VALID');