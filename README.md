https://www.alphavantage.co
API Key: J22355EMA6H0FCIS

Start Maria DB
C:\Program Files\MariaDB 10.3\bin>mysqld
C:\Program Files\MariaDB 10.3\bin>mysql -u root -p -h localhost
Password: r00t
use ticks

What is next?
-------------
1. Analysen über die Tabelle SHARE_PRICE
1a) Gibt Lücken und wenn ja welche? Mit rank() / 2 abgrundet bekommt man immer die benachbarten Elemente. Dann mit first_value() und last_Value die beiden Werte per Deivision vergleichen
1b) Fehlen Werte in der Folge?
2. Time_Series und Day_Series trennen und per FOREIGN KEY aufeinander verweisen lassen.
3. Webservices für Provider und Provider_Query
4. Webservices für die Pflege der Aktienpreisen / Wechselkurse
5. Untersuchen, welche Währung für die japanischen Aktien, für die wir Symbols haben, Werte geliefert werden


