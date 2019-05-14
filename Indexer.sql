DROP TABLE index_variation_config
DROP TABLE index_variation_history;
DROP TABLE index_variation;
DROP TABLE index_masterdata;
DROP TABLE provider_query;
DROP TABLE provider;
DROP TABLE exchange_rate;
DROP TABLE share_price;
DROP TABLE share;
DROP TABLE time_series;

CREATE TABLE currency (
  iso4217 VARCHAR(3) NOT NULL,
  PRIMARY KEY(iso4217)
);

INSERT INTO currency(iso4217) VALUES ('EUR');
INSERT INTO currency(iso4217) VALUES ('USD');

CREATE TABLE time_series(the_date DATETIME NOT NULL, UNIQUE(the_date)) AS
select * from
    (select date_add('1970-01-01', INTERVAL t7*10000000 + t6*1000000 + t5*100000 + t4*10000 + t3*1000 + t2*100 + t1*10 + t0 MINUTE) the_date from
    (select 0 t0 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t0,
    (select 0 t1 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t1,
    (select 0 t2 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t2,
    (select 0 t3 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t3,
    (select 0 t4 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t4,
    (select 0 t5 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t5,
    (select 0 t6 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t6,
    (select 0 t7 union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t7) v
Where the_date between '1998-01-01' and '2039-12-31'
;

ALTER TABLE time_series ADD PRIMARY KEY (the_date);

CREATE TABLE day_series (
    the_date DATE NOT NULL,
    PRIMARY KEY (the_date)
);

INSERT INTO day_series (the_date)
SELECT DATE(the_date)
FROM time_series
GROUP BY DATE(the_date)
;

CREATE TABLE day_series_tag (the_date DATETIME NOT NULL, tag VARCHAR(20)) AS
SELECT the_date, 'HOLIDAY' as tag
FROM day_series
WHERE DAYOFWEEK(the_date) = 1 
OR DAYOFWEEK(the_date) = 6
;

ALTER TABLE day_series_tag ADD PRIMARY KEY (the_date, tag);
ALTER TABLE day_series_tag ADD FOREIGN KEY (the_date) REFERENCES day_series(the_date);

CREATE TABLE open_high_close_low (
  id INT NOT NULL,
  name VARCHAR(5) NOT NULL,
  PRIMARY KEY (id)
);
INSERT INTO open_high_close_low VALUES (0, 'OPEN');
INSERT INTO open_high_close_low VALUES (1, 'HIGH');
INSERT INTO open_high_close_low VALUES (2, 'CLOSE');
INSERT INTO open_high_close_low VALUES (3, 'LOW');

CREATE TABLE symbol_origin (
  origin VARCHAR(50) NOT NULL,
  PRIMARY KEY (origin)
);

INSERT INTO symbol_origin (origin) VALUES ('WKN');
INSERT INTO symbol_origin (origin) VALUES ('USA');
INSERT INTO symbol_origin (origin) VALUES ('RIC');

CREATE TABLE security (
  isin VARCHAR(12) NOT NULL,
  wkn VARCHAR(6) NOT NULL,
  symbol VARCHAR(4),
  name VARCHAR(50) NOT NULL,
  currency VARCHAR(3) NOT NULL,
  PRIMARY KEY (isin)
);

INSERT INTO share (isin, wkn, symbol, name, currency) VALUES ('US5949181045', '870747', 'MSFT', 'Microsoft Corp.', 'USD');

CREATE TABLE security_symbol (
  isin VARCHAR(12) NOT NULL,
  symbol VARCHAR(20) NOT NULL,
  origin VARCHAR(50) NOT NULL,
  currency VARCHAR(3) NOT NULL,
  PRIMARY KEY (isin, symbol)
);
--CREATE UNIQUE INDEX uix_security_symbol1 PN security_model(symbol, origin);
ALTER TABLE security_symbol ADD FOREIGN KEY (isin) REFERENCES security(isin);
ALTER TABLE security_symbol ADD FOREIGN KEY (origin) REFERENCES symbol_origin(origin);
ALTER TABLE security_symbol ADD FOREIGN KEY (currency) REFERENCES currency(iso4217);

CREATE TABLE provider (
  provider_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50),
  PRIMARY KEY (provider_id)
);

INSERT INTO provider (provider_id, name) VALUES (1, 'Alphavantage');
INSERT INTO provider (provider_id, name) VALUES (2, 'Ultumus');
INSERT INTO provider (provider_id, name) VALUES (3, 'Morning Start');

CREATE TABLE provider_query (
  provider_id INT NOT NULL,
  provider_query_id INT NOT NULL AUTO_INCREMENT,
  share_exchange VARCHAR(1),
  variation VARCHAR(10),
  PRIMARY KEY (provider_query_id)
);
ALTER TABLE provider_query ADD FOREIGN KEY (provider_id) REFERENCES provider(provider_id);

INSERT INTO provider_query (provider_id, provider_query_id, share_exchange, name) VALUES (1, 1, 1, 'INTRADAY');
INSERT INTO provider_query (provider_id, provider_query_id, share_exchange, name) VALUES (1, 2, 0, 'INTRADAY');
INSERT INTO provider_query (provider_id, provider_query_id, share_exchange, name) VALUES (1, 3, 0, 'DAILY');
INSERT INTO provider_query (provider_id, provider_query_id, share_exchange, name) VALUES (1, 4, 1, 'DAILY');
INSERT INTO provider_query (provider_id, provider_query_id, share_exchange, name) VALUES (2, 5, 1, 'Ultumus');

CREATE TABLE provider_query_security_symbol (
	provider_query_id INT NOT NULL,
	isin VARCHAR(12) NOT NULL,
	symbol VARCHAR(20) NOT NULL,
	interval_in_minutes INT,
	last_run DATE,
	PRIMARY KEY (provider_query_id, isin, symbol)
);

ALTER TABLE provider_query_security_symbol ADD FOREIGN KEY (provider_query_id) REFERENCES provider_query (provider_query_id);
ALTER TABLE provider_query_security_symbol ADD FOREIGN KEY (isin, symbol) REFERENCES security_symbol (isin, symbol);

CREATE TABLE provider_query_exchange_rate (
	provider_query_id INT NOT NULL,
	currency_from VARCHAR(3) NOT NULL,
	currency_to VARCHAR(3) NOT NULL,
	interval_in_minutes INT,
	last_run DATETIME,
	PRIMARY KEY (provider_query_id, currency_from, currency_to)
);

ALTER TABLE provider_query_exchange_rate ADD FOREIGN KEY (provider_query_id) REFERENCES provider_query (provider_query_id);
ALTER TABLE provider_query_exchange_rate ADD FOREIGN KEY (currency_from) REFERENCES currency (iso4217);
ALTER TABLE provider_query_exchange_rate ADD FOREIGN KEY (currency_to) REFERENCES currency (iso4217);

INSERT INTO provider_query_exchange_rate (provider_query_id, currency_from, currency_to)
SELECT pq.provider_query_id, cf.iso4217, ct.iso4217
FROM provider_query pq, currency cf, currency ct
WHERE pq.share_exchange = 1
AND cf.iso4217 <> ct.iso4217
;


CREATE TABLE exchange_rate (
  currency_from VARCHAR(3) NOT NULL,
  currency_to VARCHAR(3) NOT NULL,
  provider_query_id INT NOT NULL,
  timestamp DATETIME NOT NULL,
  rate DECIMAL(10, 4) NOT NULL,
  PRIMARY KEY (currency_from, currency_to, provider_query_id, timestamp)
);

ALTER TABLE exchange_rate ADD FOREIGN KEY (provider_query_id, currency_from, currency_to) REFERENCES provider_query_exchange_rate (provider_query_id, currency_from, currency_to);
ALTER TABLE exchange_rate ADD FOREIGN KEY (timestamp) REFERENCES time_series(the_date);

CREATE TABLE share_price (
  provider_query_id INT NOT NULL,
  isin VARCHAR(12) NOT NULL,
  symbol VARCHAR(20) NOT NULL,
  timestamp DATETIME NOT NULL,
  open_high_close_low VARCHAR(5) NOT NULL,
  price DECIMAL(10, 4) NOT NULL,
  volume INT NOT NULL,
  PRIMARY KEY (provider_query_id, isin, symbol, timestamp, open_high_close_low)
);

ALTER TABLE share_price ADD FOREIGN KEY (timestamp) REFERENCES time_series(the_date);
ALTER TABLE share_price ADD FOREIGN KEY (provider_query_id, isin, symbol) REFERENCES provider_query_security_symbol(provider_query_id, isin, symbol);

CREATE TABLE index_family (
    family_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    parent_id INT,
    PRIMARY KEY (family_id)
);
CREATE UNIQUE INDEX uix_index_family1 ON index_family(name);

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

INSERT INTO index_masterdata (name, currency) VALUES ('Thomas first index', 'USD');

CREATE TABLE index_variation (
  index_variation_id INT NOT NULL AUTO_INCREMENT,
  masterdata_id INT NOT NULL,
  name VARCHAR(50) NOT NULL,
  PRIMARY KEY (index_variation_id)
);
CREATE UNIQUE INDEX uix_index_variation ON index_variation(masterdata_id, name);
ALTER TABLE index_variation ADD FOREIGN KEY (masterdata_id) REFERENCES index_masterdata(masterdata_id);

INSERT INTO index_variation (masterdata_id, name) VALUES (LAST_INSERT_ID(), 'Master');

CREATE TABLE index_variation_timespan (
  timespan_id INT NOT NULL,
  variation_id INT NOT NULL,
  valid_from DATE NOT NULL,
  valid_to DATE NOT NULL,
  PRIMARY KEY (timespan_id)
);
ALTER TABLE index_variation_timespan ADD FOREIGN KEY (variation_id) REFERENCES index_variation(variation_id);
ALTER TABLE index_variation_timespan ADD FOREIGN KEY (valid_from) REFERENCES time_series(the_date);
ALTER TABLE index_variation_timespan ADD FOREIGN KEY (valid_to) REFERENCES time_series(the_date);

CREATE TABLE index_variation_security (
  timespan_id INT NOT NULL,
  isin VARCHAR(12) NOT NULL,
  fraction DOUBLE(10, 8) NOT NULL,
  PRIMARY KEY (timespan_id, isin)
);

ALTER TABLE index_variation_security ADD FOREIGN KEY (timespan_id) REFERENCES index_variation_timespan(timespan_id);
ALTER TABLE index_variation_security ADD FOREIGN KEY (isin) REFERENCES security(isin);

CREATE TABLE index_variation_provider_security (
  symbol_id INT NOT NULL,
  provider_query_id INT NOT NULL,
  variation_id INT NOT NULL,
  isin VARCHAR(12) NOT NULL,
  symbol VARCHAR(20) NOT NULL,
  valid_from DATETIME NOT NULL,
  valid_to DATETIME NOT NULL,
  quality DECIMAL(10, 8) NOT NULL,
  PRIMARY KEY (symbol_id)
);

ALTER TABLE index_variation_provider_security ADD FOREIGN KEY (isin, symbol, provider_query_id) REFERENCES provider_query_security_symbol (isin, symbol, provider_query_id);
ALTER TABLE index_variation_provider_security ADD FOREIGN KEY (variation_id) REFERENCES index_variation(variation_id);
ALTER TABLE index_variation_provider_security ADD FOREIGN KEY (valid_from) REFERENCES time_series(the_date);
ALTER TABLE index_variation_provider_security ADD FOREIGN KEY (valid_to) REFERENCES time_series(the_date);

CREATE TABLE index_variation_provider_exchange (
    provider_exchange_id INT NOT NULL,
    currency_from VARCHAR(3) NOT NULL,
    currency_to VARCHAR(3) NOT NULL,
    provider_query_id INT NOT NULL,
    quality DOUBLE NOT NULL,
    valid_from DATETIME NOT NULL,
    valid_to DATETIME NOT NULL,
    variation_id INT NOT NULL,
    PRIMARY KEY (provider_exchange_id)
);

ALTER TABLE index_variation_provider_exchange ADD FOREIGN KEY (provider_query_id, currency_from, currency_to) REFERENCES provider_query_exchange_rate(provider_query_id, currency_from, currency_to);
ALTER TABLE index_variation_provider_exchange ADD FOREIGN KEY (variation_id) REFERENCES index_variation(variation_id);
ALTER TABLE index_variation_provider_exchange ADD FOREIGN KEY (valid_from) REFERENCES time_series(the_date);
ALTER TABLE index_variation_provider_exchange ADD FOREIGN KEY (valid_to) REFERENCES time_series(the_date);

CREATE OR REPLACE VIEW DAILY_PRICE_CLOSE AS
SELECT isin, symbol, provider_query_id, timestamp, date(timestamp) as the_day, price, volume, open_high_close_low
FROM (
SELECT ROW_NUMBER() over (partition by isin, symbol, provider_query_id, date(timestamp) order by timestamp desc) as row_num,
       SUM(volume) over (partition by isin, symbol, provider_query_id, date(timestamp) order by price) as volume, 
	   isin, symbol, provider_query_id, timestamp, price, date(timestamp) as the_day, open_high_close_low
FROM share_price sp
WHERE open_high_close_low = 2
) day_price
WHERE day_price.row_num = 1
;

CREATE OR REPLACE VIEW DAILY_PRICE_OPEN AS 
SELECT isin, symbol, provider_query_id, timestamp, date(timestamp) as the_day, price, volume, open_high_close_low
FROM (
SELECT ROW_NUMBER() over (partition by isin, symbol, provider_query_id, date(timestamp) order by timestamp) as row_num,
       SUM(volume) over (partition by isin, symbol, provider_query_id, date(timestamp) order by price) as volume, 
	   isin, symbol, provider_query_id, timestamp, price, date(timestamp) as the_day, open_high_close_low
FROM share_price sp
WHERE open_high_close_low = 0
) day_price
WHERE day_price.row_num = 1
;

CREATE OR REPLACE VIEW DAILY_PRICE_HIGH AS 
SELECT isin, symbol, provider_query_id, timestamp, date(timestamp) as the_day, price, volume, open_high_close_low
FROM (
SELECT ROW_NUMBER() over (partition by isin, symbol, provider_query_id, date(timestamp) order by price desc) as row_num,
       SUM(volume) over (partition by isin, symbol, provider_query_id, date(timestamp) order by price) as volume, 
	   isin, symbol, provider_query_id, timestamp, price, date(timestamp) as the_day, open_high_close_low
FROM share_price sp
WHERE open_high_close_low = 1
) day_price
WHERE day_price.row_num = 1
;

CREATE OR REPLACE VIEW DAILY_PRICE_LOW AS 
SELECT isin, symbol, provider_query_id, timestamp, the_day, price, volume, open_high_close_low
FROM (
SELECT ROW_NUMBER() over (partition by isin, symbol, provider_query_id, date(timestamp) order by price) as row_num, 
       SUM(volume) over (partition by isin, symbol, provider_query_id, date(timestamp) order by price) as volume, 
	   isin, symbol, provider_query_id, timestamp, price, date(timestamp) as the_day, open_high_close_low
FROM share_price sp
WHERE open_high_close_low = 2
) day_price
WHERE day_price.row_num = 1
;

CREATE OR REPLACE VIEW DAILY_PRICE AS
SELECT * FROM DAILY_PRICE_OPEN
UNION ALL 
SELECT * FROM DAILY_PRICE_HIGH
UNION ALL 
SELECT * FROM DAILY_PRICE_CLOSE
UNION ALL 
SELECT * FROM DAILY_PRICE_LOW
;

SELECT ivs.isin, min(ivt.valid_from), max(ivt.valid_to)
FROM index_variation iv
JOIN index_variation_timespan ivt ON iv.variation_id = ivt.variation_id
JOIN index_variation_security ivs ON ivt.timespan_id = ivs.timespan_id
WHERE iv.variation_id = 14
GROUP BY ivs.isin
;

CREATE TABLE calc_security (
  name VARCHAR(50) NOT NULL,
  variation_id INT(11) NOT NULL,
  timestamp DATETIME NOT NULL,
  index_currency VARCHAR(3) NOT NULL,
  the_date DATETIME NOT NULL,
  timespan_id INT(11) NOT NULL,
  security_name VARCHAR(50) NOT NULL,
  isin VARCHAR(12) NOT NULL,
  symbol VARCHAR(20) NOT NULL,
  open_high_close_low VARCHAR(5) NOT NULL,
  price DOUBLE NOT NULL,
  fraction DOUBLE NOT NULL,
  symbol_currency VARCHAR(3) NOT NULL,
  exchange_rate DOUBLE NOT NULL,
  provider VARCHAR(50) NOT NULL,
  query VARCHAR(50) NOT NULL,
  provider_query_id INT(11) NOT NULL,
  PRIMARY KEY (name, variation_id, the_date, isin)
);

CREATE OR REPLACE VIEW vw_calc_security_simple AS
SELECT iv.variation_id, now() AS timestamp, im.currency AS index_currency, ts.the_date, ivt.timespan_id, 
       s.name AS security_name, ivs.isin, price.symbol, ohcl.name as open_high_close_low, 
       price.price, ivs.fraction, price.currency AS symbol_currency,
       CASE im.currency WHEN price.currency THEN 1 ELSE exchange.rate END AS exchange_rate,
	   p.name AS provider, pq.name AS query, pq.provider_query_id
FROM index_variation iv
JOIN index_masterdata im ON im.masterdata_id = iv.masterdata_id
JOIN index_variation_timespan ivt ON iv.variation_id = ivt.variation_id
JOIN index_variation_security ivs ON ivt.timespan_id = ivs.timespan_id
JOIN security s ON ivs.isin = s.isin
JOIN time_series ts ON ts.the_date >= ivt.valid_from AND ts.the_date < ivt.valid_to
JOIN (
	SELECT *
	FROM (
		SELECT ROW_NUMBER() over (PARTITION BY sp.timestamp, sp.isin, sp.open_high_close_low ORDER BY ivps.quality DESC) AS row_num,
		       sp.timestamp, sp.isin, sp.symbol, sp.price, sp.volume, sp.open_high_close_low, ivps.provider_query_id, ss.currency, ivps.variation_id
		FROM share_price sp 
		JOIN index_variation_provider_security ivps ON sp.timestamp >= ivps.valid_from AND sp.timestamp < ivps.valid_to AND sp.isin = ivps.isin AND sp.symbol = ivps.symbol AND sp.provider_query_id = ivps.provider_query_id
		JOIN security_symbol ss ON ss.isin = sp.isin AND ss.symbol = sp.symbol
	) my1
	WHERE row_num = 1
) price ON price.timestamp = ts.the_date AND price.isin = ivs.isin AND price.variation_id = iv.variation_id
JOIN open_high_close_low ohcl ON ohcl.id = price.open_high_close_low
JOIN provider_query pq on pq.provider_query_id = price.provider_query_id
JOIN provider p ON p.provider_id = pq.provider_id
JOIN time_series_tag tst ON ts.the_date = tst.the_date AND tag = 'DAY'
LEFT JOIN (
	SELECT *
	FROM (
		SELECT ROW_NUMBER() OVER (PARTITION BY er.timestamp, er.currency_from, er.currency_to, er.open_high_close_low ORDER BY ivpe.quality DESC) AS row_num,
		       er.timestamp, er.currency_from, er.currency_to, er.open_high_close_low, ivpe.variation_id, er.rate
		FROM exchange_rate er
		JOIN index_variation_provider_exchange ivpe ON er.timestamp >= ivpe.valid_from AND er.timestamp < ivpe.valid_to AND er.provider_query_id = ivpe.provider_query_id AND er.currency_from = ivpe.currency_from AND er.currency_to = ivpe.currency_to
	) my2
	WHERE row_num = 1
) exchange ON exchange.timestamp = ts.the_date AND exchange.currency_from = price.currency AND exchange.currency_to = im.currency AND exchange.variation_id = iv.variation_id AND exchange.open_high_close_low = ohcl.id
;

INSERT INTO calc_security (SELECT 'CALC1', css.* FROM vw_calc_security_simple css WHERE css.variation_id = 14 AND css.open_high_close_low = 'CLOSE');

CREATE TABLE calc_index (
	variation_id INT NOT NULL,
	name VARCHAR(50) NOT NULL,
	the_date DATETIME NOT NULL,
	index_value DOUBLE NOT NULL,
	PRIMARY KEY (variation_id, name, the_date)
);
ALTER TABLE calc_index ADD FOREIGN KEY (variation_id) REFERENCES index_variation(variation_id);
ALTER TABLE calc_index ADD FOREIGN KEY (the_date) REFERENCES time_series(the_date);

INSERT INTO calc_index (
SELECT variation_id, name, the_date, SUM(price * exchange_rate * fraction) AS index_value
FROM calc_security
WHERE variation_id = ?
AND name = ?
GROUP BY variation_id, name, the_date
)
;

SELECT the_date, index_value
FROM calc_index
WHERE variation_id = 14
AND name = 'CALC2'
INTO OUTFILE '/tmp/index.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
;

-- Die folgenden SQLs können verwendet werden, um Sprünge im Kursverlauf festzustellen.

CREATE VIEW vw_rate_between_two_quotes AS
SELECT provider_query_id, isin, symbol, open_high_close_low, timestamp, price, price_before, volume
FROM (
    SELECT *
    FROM (
        SELECT FIRST_VALUE(price) OVER (PARTITION BY provider_query_id, isin, symbol, open_high_close_low, TRUNCATE(row_num / 2, 0) ORDER BY timestamp) AS price_before,
               x.*
        FROM (
            SELECT ROW_NUMBER() OVER (PARTITION BY provider_query_id, isin, symbol, open_high_close_low ORDER BY timestamp) AS row_num, sp.*
            FROM share_price sp
        ) x
    ) y1
    WHERE row_num % 2 = 1
    UNION ALL
    SELECT *
    FROM (
        SELECT FIRST_VALUE(price) OVER (PARTITION BY provider_query_id, isin, symbol, open_high_close_low, TRUNCATE((row_num - 1) / 2, 0) ORDER BY timestamp) AS price_before,
               x.*
        FROM (
            SELECT ROW_NUMBER() OVER (PARTITION BY provider_query_id, isin, symbol, open_high_close_low ORDER BY timestamp) AS row_num, sp.*
            FROM share_price sp
        ) x
    ) y2
    WHERE row_num % 2 = 0
) z;

SELECT *
FROM vw_rate_between_two_quotes
WHERE symbol = 'MSFT'
AND provider_query_id = 3
AND open_high_close_low = 0
AND price / price_before < 0.85
ORDER BY timestamp
LIMIT 10
;

-- Welche disketen Zeitintervalle gibt es innerhalb des Zeitraums?

WITH RECURSIVE cte (timespan_id, variation_id, valid_from, valid_to, uuid) as (
  SELECT timespan_id, variation_id, valid_from, valid_to, UUID() AS uuid
  FROM   index_variation_timespan ivt
  WHERE  NOT EXISTS (SELECT 1 FROM index_variation_timespan WHERE valid_to = ivt.valid_from)
  UNION ALL
  SELECT ivt.timespan_id, ivt.variation_id, ivt.valid_from, ivt.valid_to, cte.uuid
  FROM   index_variation_timespan ivt
  JOIN   cte ON ivt.valid_from = cte.valid_to
)
CREATE VIEW vw_index_variation_discrete_timespan AS
SELECT variation_id, uuid, MIN(valid_from) AS valid_from, MAX(valid_to) AS valid_to
FROM cte
GROUP BY variation_id, uuid
ORDER BY valid_from
;

WITH RECURSIVE cte (variation_id, isin, valid_from, valid_to, uuid) as (
  SELECT ivt.variation_id, ivs.isin, ivt.valid_from, ivt.valid_to, UUID() AS uuid
  FROM   index_variation_timespan ivt
  JOIN   index_variation_security ivs ON ivt.timespan_id = ivs.timespan_id
  WHERE  NOT EXISTS (SELECT 1 FROM index_variation_timespan WHERE valid_to = ivt.valid_from)
  UNION ALL
  SELECT ivt.variation_id, ivs.isin, ivt.valid_from, ivt.valid_to, cte.uuid
  FROM   index_variation_timespan ivt
  JOIN   index_variation_security ivs ON ivt.timespan_id = ivs.timespan_id
  JOIN   cte ON ivt.valid_from = cte.valid_to AND ivt.variation_id = cte.variation_id AND ivs.isin = cte.isin
)
SELECT variation_id, isin, uuid, MIN(valid_from) AS valid_from, MAX(valid_to) AS valid_to
-- SELECT variation_id, isin, uuid, valid_from AS valid_from, valid_to AS valid_to
FROM cte
GROUP BY variation_id, isin, uuid
ORDER BY isin, valid_from
;
