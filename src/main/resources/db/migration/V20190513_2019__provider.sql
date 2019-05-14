CREATE TABLE provider (
  provider_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50),
  PRIMARY KEY (provider_id)
);

INSERT INTO provider (provider_id, name) VALUES (1, 'Alphavantage');
INSERT INTO provider (provider_id, name) VALUES (2, 'Ultumus');
INSERT INTO provider (provider_id, name) VALUES (3, 'Morning Start');
