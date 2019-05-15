ALTER TABLE provider_symbol ADD COLUMN currency VARCHAR(3) NOT NULL;

ALTER TABLE provider_symbol ADD FOREIGN KEY (currency) REFERENCES currency (iso4217);
