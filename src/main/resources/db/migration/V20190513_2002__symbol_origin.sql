CREATE TABLE symbol_origin (
  origin VARCHAR(50) NOT NULL,
  PRIMARY KEY (origin)
);

INSERT INTO symbol_origin (origin) VALUES ('WKN');
INSERT INTO symbol_origin (origin) VALUES ('USA');
INSERT INTO symbol_origin (origin) VALUES ('RIC');
INSERT INTO symbol_origin (origin) VALUES ('MorningStart');
