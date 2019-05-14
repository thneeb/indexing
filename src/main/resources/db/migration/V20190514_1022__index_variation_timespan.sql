CREATE TABLE index_variation_timespan (
  timespan_id INT NOT NULL AUTO_INCREMENT,
  variation_id INT NOT NULL,
  valid_from DATETIME NOT NULL,
  valid_to DATETIME NOT NULL,
  PRIMARY KEY (timespan_id)
);
ALTER TABLE index_variation_timespan ADD FOREIGN KEY (variation_id) REFERENCES index_variation(variation_id);
ALTER TABLE index_variation_timespan ADD FOREIGN KEY (valid_from) REFERENCES time_series(the_date);
ALTER TABLE index_variation_timespan ADD FOREIGN KEY (valid_to) REFERENCES time_series(the_date);
