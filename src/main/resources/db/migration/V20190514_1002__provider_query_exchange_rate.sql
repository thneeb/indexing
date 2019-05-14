CREATE TABLE provider_query_exchange_rate (
	provider_query_id INT NOT NULL,
	currency_from VARCHAR(3) NOT NULL,
	currency_to VARCHAR(3) NOT NULL,
	interval_in_minutes INT,
	last_run DATETIME,
	PRIMARY KEY (provider_query_id, currency_from, currency_to)
);

ALTER TABLE provider_query_exchange_rate ADD FOREIGN KEY (provider_query_id) REFERENCES provider_query (provider_query_id);
ALTER TABLE provider_query_exchange_rate ADD FOREIGN KEY (currency_from) REFERENCES currency (iso4217);
ALTER TABLE provider_query_exchange_rate ADD FOREIGN KEY (currency_to) REFERENCES currency (iso4217);
