-- Create tables
CREATE TABLE STOCK_EXCHANGE (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL ,
    live_in_market BOOLEAN
);

CREATE TABLE STOCK (
   id SERIAL PRIMARY KEY,
   name VARCHAR(255) NOT NULL UNIQUE,
   description VARCHAR(255),
   current_price DECIMAL(10, 2),
   last_update TIMESTAMP
);

CREATE TABLE STOCK_EXCHANGE_STOCKS (
   stock_exchange_id INT NOT NULL,
   stock_id INT NOT NULL,
   PRIMARY KEY (stock_exchange_id, stock_id),
   FOREIGN KEY (stock_exchange_id) REFERENCES STOCK_EXCHANGE(id),
   FOREIGN KEY (stock_id) REFERENCES STOCK(id)
);
