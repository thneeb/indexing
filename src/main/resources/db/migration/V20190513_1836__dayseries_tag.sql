CREATE TABLE day_series_tag (the_date DATE NOT NULL, tag VARCHAR(20)) AS
SELECT the_date, 'HOLIDAY' as tag
FROM day_series
WHERE DAYOFWEEK(the_date) = 1
OR DAYOFWEEK(the_date) = 6
;

ALTER TABLE day_series_tag ADD PRIMARY KEY (the_date, tag);

ALTER TABLE day_series_tag ADD FOREIGN KEY (the_date) REFERENCES day_series(the_date);
