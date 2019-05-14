CREATE TABLE security (
  isin VARCHAR(12) NOT NULL,
  wkn VARCHAR(6) NOT NULL,
  symbol VARCHAR(4),
  name VARCHAR(50) NOT NULL,
  currency VARCHAR(3) NOT NULL,
  PRIMARY KEY (isin)
);

