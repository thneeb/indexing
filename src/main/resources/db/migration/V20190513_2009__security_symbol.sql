CREATE TABLE security_symbol (
  isin VARCHAR(12) NOT NULL,
  symbol VARCHAR(20) NOT NULL,
  origin VARCHAR(50) NOT NULL,
  currency VARCHAR(3) NOT NULL,
  PRIMARY KEY (isin, symbol)
);

-- CREATE UNIQUE INDEX uix_security_symbol1 ON security_model(symbol, origin);
ALTER TABLE security_symbol ADD FOREIGN KEY (isin) REFERENCES security(isin);
ALTER TABLE security_symbol ADD FOREIGN KEY (origin) REFERENCES symbol_origin(origin);
ALTER TABLE security_symbol ADD FOREIGN KEY (currency) REFERENCES currency(iso4217);
