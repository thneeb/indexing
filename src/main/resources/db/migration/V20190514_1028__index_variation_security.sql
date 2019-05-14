CREATE TABLE index_variation_security (
  timespan_id INT NOT NULL,
  isin VARCHAR(12) NOT NULL,
  fraction DOUBLE(10, 8) NOT NULL,
  PRIMARY KEY (timespan_id, isin)
);

ALTER TABLE index_variation_security ADD FOREIGN KEY (timespan_id) REFERENCES index_variation_timespan(timespan_id);
ALTER TABLE index_variation_security ADD FOREIGN KEY (isin) REFERENCES security(isin);
