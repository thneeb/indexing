INSERT INTO provider_query_security_symbol (provider_id, provider_query_id, isin, symbol)
(SELECT provider_id, 6, isin, symbol FROM provider_symbol WHERE provider_id = 3);
