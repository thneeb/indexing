CREATE TABLE provider_query (
  provider_id INT NOT NULL,
  provider_query_id INT NOT NULL AUTO_INCREMENT,
  share_exchange VARCHAR(1),
  name VARCHAR(10),
  PRIMARY KEY (provider_query_id)
);
ALTER TABLE provider_query ADD FOREIGN KEY (provider_id) REFERENCES provider(provider_id);

INSERT INTO provider_query (provider_id, provider_query_id, share_exchange, name) VALUES (1, 1, 1, 'INTRADAY');
INSERT INTO provider_query (provider_id, provider_query_id, share_exchange, name) VALUES (1, 2, 0, 'INTRADAY');
INSERT INTO provider_query (provider_id, provider_query_id, share_exchange, name) VALUES (1, 3, 0, 'DAILY');
INSERT INTO provider_query (provider_id, provider_query_id, share_exchange, name) VALUES (1, 4, 1, 'DAILY');
INSERT INTO provider_query (provider_id, provider_query_id, share_exchange, name) VALUES (2, 5, 1, 'Ultumus');
