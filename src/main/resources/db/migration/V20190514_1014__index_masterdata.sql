CREATE TABLE index_masterdata (
  masterdata_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  currency VARCHAR(3) NOT NULL,
  family_id INT,
  PRIMARY KEY (masterdata_id)
);

CREATE UNIQUE INDEX uix_index_masterdata1 ON index_masterdata(name);
ALTER TABLE index_masterdata ADD FOREIGN KEY (currency) REFERENCES currency(iso4217);
ALTER TABLE index_masterdata ADD FOREIGN KEY (family_id) REFERENCES index_family(family_id);
