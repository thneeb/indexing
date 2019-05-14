CREATE TABLE index_variation_provider_security (
  symbol_id INT NOT NULL,
  provider_query_id INT NOT NULL,
  variation_id INT NOT NULL,
  isin VARCHAR(12) NOT NULL,
  symbol VARCHAR(20) NOT NULL,
  valid_from DATETIME NOT NULL,
  valid_to DATETIME NOT NULL,
  quality DECIMAL(10, 8) NOT NULL,
  PRIMARY KEY (symbol_id)
);

ALTER TABLE index_variation_provider_security ADD FOREIGN KEY (isin, symbol, provider_query_id) REFERENCES provider_query_security_symbol (isin, symbol, provider_query_id);
ALTER TABLE index_variation_provider_security ADD FOREIGN KEY (variation_id) REFERENCES index_variation(variation_id);
ALTER TABLE index_variation_provider_security ADD FOREIGN KEY (valid_from) REFERENCES time_series(the_date);
ALTER TABLE index_variation_provider_security ADD FOREIGN KEY (valid_to) REFERENCES time_series(the_date);
