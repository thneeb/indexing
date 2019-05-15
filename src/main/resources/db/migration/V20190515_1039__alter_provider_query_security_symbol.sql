DROP TABLE IF EXISTS index_variation_provider_security;
DROP TABLE IF EXISTS share_price;
DROP TABLE IF EXISTS provider_query_security_symbol;

CREATE TABLE provider_query_security_symbol (
    provider_id INT NOT NULL,
	provider_query_id INT NOT NULL,
	isin VARCHAR(12) NOT NULL,
	symbol VARCHAR(20) NOT NULL,
	interval_in_minutes INT,
	last_run DATE,
	PRIMARY KEY (provider_query_id, isin, symbol)
);

ALTER TABLE provider_query_security_symbol ADD FOREIGN KEY (provider_query_id) REFERENCES provider_query (provider_query_id);
ALTER TABLE provider_query_security_symbol ADD FOREIGN KEY (provider_id, isin, symbol) REFERENCES provider_symbol (provider_id, isin, symbol);

CREATE TABLE index_variation_provider_security (
  symbol_id INT NOT NULL,
  provider_query_id INT NOT NULL,
  variation_id INT NOT NULL,
  isin VARCHAR(12) NOT NULL,
  symbol VARCHAR(50) NOT NULL,
  valid_from DATETIME NOT NULL,
  valid_to DATETIME NOT NULL,
  PRIMARY KEY (symbol_id)
);

ALTER TABLE index_variation_provider_security ADD FOREIGN KEY (provider_query_id, isin, symbol) REFERENCES provider_query_security_symbol (provider_query_id, isin, symbol);
ALTER TABLE index_variation_provider_security ADD FOREIGN KEY (variation_id) REFERENCES index_variation(variation_id);
ALTER TABLE index_variation_provider_security ADD FOREIGN KEY (valid_from) REFERENCES time_series(the_date);
ALTER TABLE index_variation_provider_security ADD FOREIGN KEY (valid_to) REFERENCES time_series(the_date);

CREATE TABLE share_price (
  provider_query_id INT NOT NULL,
  isin VARCHAR(12) NOT NULL,
  symbol VARCHAR(50) NOT NULL,
  timestamp DATETIME NOT NULL,
  open_high_close_low VARCHAR(5) NOT NULL,
  price DECIMAL(10, 4) NOT NULL,
  volume INT NOT NULL,
  PRIMARY KEY (provider_query_id, isin, symbol, timestamp, open_high_close_low)
);

ALTER TABLE share_price ADD FOREIGN KEY (timestamp) REFERENCES time_series(the_date);
ALTER TABLE share_price ADD FOREIGN KEY (provider_query_id, isin, symbol) REFERENCES provider_query_security_symbol(provider_query_id, isin, symbol);
