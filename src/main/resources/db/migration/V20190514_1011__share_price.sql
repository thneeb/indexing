CREATE TABLE share_price (
  provider_query_id INT NOT NULL,
  isin VARCHAR(12) NOT NULL,
  symbol VARCHAR(20) NOT NULL,
  timestamp DATETIME NOT NULL,
  open_high_close_low VARCHAR(5) NOT NULL,
  price DECIMAL(10, 4) NOT NULL,
  volume INT NOT NULL,
  PRIMARY KEY (provider_query_id, isin, symbol, timestamp, open_high_close_low)
);

ALTER TABLE share_price ADD FOREIGN KEY (timestamp) REFERENCES time_series(the_date);
ALTER TABLE share_price ADD FOREIGN KEY (provider_query_id, isin, symbol) REFERENCES provider_query_security_symbol(provider_query_id, isin, symbol);
