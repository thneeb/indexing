CREATE TABLE day_series (
    the_date DATE NOT NULL,
    PRIMARY KEY (the_date)
);

INSERT INTO day_series (the_date)
SELECT DATE(the_date)
FROM time_series
GROUP BY DATE(the_date)
;
