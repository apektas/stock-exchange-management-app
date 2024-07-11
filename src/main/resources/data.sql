-- Insert data into STOCK_EXCHANGE
INSERT INTO STOCK_EXCHANGE (name, description, live_in_market)
VALUES ('NYSE', 'New York Stock Exchange', false);
INSERT INTO STOCK_EXCHANGE (name, description, live_in_market)
VALUES ('BIST', 'Borsa Istanbul', false);
INSERT INTO STOCK_EXCHANGE (name, description, live_in_market)
VALUES ('NASDAQ', 'Nasdaq', false);
INSERT INTO STOCK_EXCHANGE (name, description, live_in_market)
VALUES ('LSE', 'London Stock Exchange', false);
INSERT INTO STOCK_EXCHANGE (name, description, live_in_market)
VALUES ('JPX', 'Japan Exchange Group', false);

-- Insert data into STOCK
INSERT INTO STOCK (name, description, current_price, last_update)
VALUES ('AAPL', 'Apple Inc.', 10.0, '2024-07-11 11:00:00');
INSERT INTO STOCK (name, description, current_price, last_update)
VALUES ('TSLA', 'TSLA Inc.', 20.0, '2024-07-11 11:00:00');
INSERT INTO STOCK (name, description, current_price, last_update)
VALUES ('BOING', 'BOING', 30.0, '2024-07-11 11:00:00');
INSERT INTO STOCK (name, description, current_price, last_update)
VALUES ('MCRSFT', 'MCRSFT Inc.', 40.0, '2024-07-11 11:00:00');
INSERT INTO STOCK (name, description, current_price, last_update)
VALUES ('HEPS', 'D Market Elektronik Hztlr ve Tcrt AS-ADR', 50.0, '2024-07-11 11:00:00');
INSERT INTO STOCK (name, description, current_price, last_update)
VALUES ('TCELL', 'TCELL Inc.', 60.0, '2024-07-11 11:00:00');
INSERT INTO STOCK (name, description, current_price, last_update)
VALUES ('TPRS', 'TPRS Inc.', 70.0, '2024-07-11 11:00:00');
INSERT INTO STOCK (name, description, current_price, last_update)
VALUES ('GOOGL', 'Google Inc.', 80.0, '2024-07-11 11:00:00');
INSERT INTO STOCK (name, description, current_price, last_update)
VALUES ('AMZN', 'Amazon Inc.', 90.0, '2024-07-11 11:00:00');
INSERT INTO STOCK (name, description, current_price, last_update)
VALUES ('FB', 'Facebook Inc.', 100.0, '2024-07-11 11:00:00');
INSERT INTO STOCK (name, description, current_price, last_update)
VALUES ('NFLX', 'Netflix Inc.', 110.0, '2024-07-11 11:00:00');
INSERT INTO STOCK (name, description, current_price, last_update)
VALUES ('NVDA', 'Nvidia Corp.', 120.0, '2024-07-11 11:00:00');

-- Insert data into STOCK_EXCHANGE_STOCKS
INSERT INTO STOCK_EXCHANGE_STOCKS (stock_exchange_id, stock_id)
VALUES (1, 1);
INSERT INTO STOCK_EXCHANGE_STOCKS (stock_exchange_id, stock_id)
VALUES (1, 2);
INSERT INTO STOCK_EXCHANGE_STOCKS (stock_exchange_id, stock_id)
VALUES (1, 3);
INSERT INTO STOCK_EXCHANGE_STOCKS (stock_exchange_id, stock_id)
VALUES (3, 7);
INSERT INTO STOCK_EXCHANGE_STOCKS (stock_exchange_id, stock_id)
VALUES (3, 8);
INSERT INTO STOCK_EXCHANGE_STOCKS (stock_exchange_id, stock_id)
VALUES (3, 9);
INSERT INTO STOCK_EXCHANGE_STOCKS (stock_exchange_id, stock_id)
VALUES (4, 10);
INSERT INTO STOCK_EXCHANGE_STOCKS (stock_exchange_id, stock_id)
VALUES (4, 11);
INSERT INTO STOCK_EXCHANGE_STOCKS (stock_exchange_id, stock_id)
VALUES (4, 12);
INSERT INTO STOCK_EXCHANGE_STOCKS (stock_exchange_id, stock_id)
VALUES (5, 1);
INSERT INTO STOCK_EXCHANGE_STOCKS (stock_exchange_id, stock_id)
VALUES (5, 2);
INSERT INTO STOCK_EXCHANGE_STOCKS (stock_exchange_id, stock_id)
VALUES (5, 3);
INSERT INTO STOCK_EXCHANGE_STOCKS (stock_exchange_id, stock_id)
VALUES (5, 4);