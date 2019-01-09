https://www.alphavantage.co
API Key: J22355EMA6H0FCIS

Start Maria DB
C:\Program Files\MariaDB 10.3\bin>mysqld
C:\Program Files\MariaDB 10.3\bin>mysql -u root -p -h localhost
Password: r00t
use ticks

What is next?
-------------
- Analysen über die Tabelle SHARE_PRICE
-- Gibt Lücken und wenn ja welche?
-- Fehlen Werte in der Folge?
- Webservices für die Pflege der Aktienpreisen / Wechselkurse
- Untersuchen, welche Währung für die japanischen Aktien, für die wir Symbols haben, Werte geliefert werden
- Verification eines Index über einen gewissen Zeitraum
-- Sind immer Securites definiert
-- Ist für jede Security ein Provider definiert
-- Ist der Provider für die Abschnitte definiert, wo die Security im Index vertreten ist
-- Ist für jede verwendete Währung ein Provider definiert
-- Ist der Provider für die Abschnitte definiert, wo die Währung im Index benötit wird
-- Ergeben die Gewichtungen immer 1
- Löschen von Zeitabschnitten in Security, Provider Exchange, Provider Security
- Ändern von Abschnitten in Security, Provider Exchange, Provider Security

Under construction
------------------
- Time_Series und Day_Series trennen und per FOREIGN KEY aufeinander verweisen lassen.

Done
----
- Webservices für Provider und Provider_Query
- Crawler für die Umtauschkurse
- Analysen über die Tabelle SHARE_PRICE
-- Gibt es Aktiensplits? Mit rank() / 2 abgrundet bekommt man immer die benachbarten Elemente. Dann mit first_value() und last_Value die beiden Werte per Deivision vergleichen

Clone from TEF
--------------
git config --global http.proxy 10.119.18.5:8080
git config --global https.proxy 10.119.18.5:8080
git -c http.sslVerify=false clone https://github.com/thneeb/indexing.git
