ALTER TABLE provider_query MODIFY COLUMN name VARCHAR(50);

INSERT INTO provider_query (provider_id, provider_query_id, share_exchange, name) VALUES (3, 6, 0, 'Realtime Share');
INSERT INTO provider_query (provider_id, provider_query_id, share_exchange, name) VALUES (3, 7, 1, 'Realtime Exchange');
