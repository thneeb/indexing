CREATE TABLE exchange_rate (
  currency_from VARCHAR(3) NOT NULL,
  currency_to VARCHAR(3) NOT NULL,
  provider_query_id INT NOT NULL,
  timestamp DATETIME NOT NULL,
  rate DECIMAL(10, 4) NOT NULL,
  PRIMARY KEY (currency_from, currency_to, provider_query_id, timestamp)
);

ALTER TABLE exchange_rate ADD FOREIGN KEY (provider_query_id, currency_from, currency_to) REFERENCES provider_query_exchange_rate (provider_query_id, currency_from, currency_to);
ALTER TABLE exchange_rate ADD FOREIGN KEY (timestamp) REFERENCES time_series(the_date);
