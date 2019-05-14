CREATE TABLE provider_query_security_symbol (
	provider_query_id INT NOT NULL,
	isin VARCHAR(12) NOT NULL,
	symbol VARCHAR(20) NOT NULL,
	interval_in_minutes INT,
	last_run DATE,
	PRIMARY KEY (provider_query_id, isin, symbol)
);

ALTER TABLE provider_query_security_symbol ADD FOREIGN KEY (provider_query_id) REFERENCES provider_query (provider_query_id);
ALTER TABLE provider_query_security_symbol ADD FOREIGN KEY (isin, symbol) REFERENCES security_symbol (isin, symbol);
