CREATE TABLE provider_symbol (
    provider_id INT NOT NULL,
    isin VARCHAR(12) NOT NULL,
    symbol VARCHAR(50) NOT NULL,
    PRIMARY KEY(provider_id, isin, symbol)
);

ALTER TABLE provider_symbol ADD FOREIGN KEY (provider_id) REFERENCES provider (provider_id);
ALTER TABLE provider_symbol ADD FOREIGN KEY (isin) REFERENCES security (isin);
