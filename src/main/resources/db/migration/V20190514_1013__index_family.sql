CREATE TABLE index_family (
    family_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    parent_id INT,
    PRIMARY KEY (family_id)
);
CREATE UNIQUE INDEX uix_index_family1 ON index_family(name);
