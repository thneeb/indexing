CREATE TABLE index_variation_provider_exchange (
    provider_exchange_id INT NOT NULL,
    currency_from VARCHAR(3) NOT NULL,
    currency_to VARCHAR(3) NOT NULL,
    provider_query_id INT NOT NULL,
    quality DOUBLE NOT NULL,
    valid_from DATETIME NOT NULL,
    valid_to DATETIME NOT NULL,
    variation_id INT NOT NULL,
    PRIMARY KEY (provider_exchange_id)
);

ALTER TABLE index_variation_provider_exchange ADD FOREIGN KEY (provider_query_id, currency_from, currency_to) REFERENCES provider_query_exchange_rate(provider_query_id, currency_from, currency_to);
ALTER TABLE index_variation_provider_exchange ADD FOREIGN KEY (variation_id) REFERENCES index_variation(variation_id);
ALTER TABLE index_variation_provider_exchange ADD FOREIGN KEY (valid_from) REFERENCES time_series(the_date);
ALTER TABLE index_variation_provider_exchange ADD FOREIGN KEY (valid_to) REFERENCES time_series(the_date);
