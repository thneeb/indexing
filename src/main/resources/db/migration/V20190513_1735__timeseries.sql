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
