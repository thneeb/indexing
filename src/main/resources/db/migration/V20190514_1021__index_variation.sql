CREATE TABLE index_variation (
  variation_id INT NOT NULL AUTO_INCREMENT,
  masterdata_id INT NOT NULL,
  name VARCHAR(50) NOT NULL,
  PRIMARY KEY (variation_id)
);
CREATE UNIQUE INDEX uix_index_variation ON index_variation(masterdata_id, name);
ALTER TABLE index_variation ADD FOREIGN KEY (masterdata_id) REFERENCES index_masterdata(masterdata_id);
