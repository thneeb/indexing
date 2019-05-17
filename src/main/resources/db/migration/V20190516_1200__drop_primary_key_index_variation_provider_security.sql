-- ALTER TABLE index_variation_provider_security DROP PRIMARY KEY (symbol_id);

ALTER TABLE index_variation_provider_security MODIFY COLUMN symbol_id INT NOT NULL AUTO_INCREMENT;

-- ALTER TABLE index_variation_provider_security ADD PRIMARY KEY (symbol_id);
