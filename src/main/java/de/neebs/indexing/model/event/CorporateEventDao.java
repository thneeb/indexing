package de.neebs.indexing.model.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CorporateEventDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<SecurityTimespan> retrieveSecurities(int variationId) {
        StringBuilder sb = new StringBuilder();
        sb.append("WITH RECURSIVE cte (timespan_id, variation_id, valid_from, valid_to, uuid) as (");
        sb.append("SELECT timespan_id, variation_id, valid_from, valid_to, UUID() AS uuid ");
        sb.append("FROM index_variation_timespan ivt ");
        sb.append("WHERE  NOT EXISTS (SELECT 1 FROM index_variation_timespan WHERE valid_to = ivt.valid_from) ");
        sb.append("UNION ALL ");
        sb.append("SELECT ivt.timespan_id, ivt.variation_id, ivt.valid_from, ivt.valid_to, cte.uuid ");
        sb.append("FROM index_variation_timespan ivt ");
        sb.append("JOIN cte ON ivt.valid_from = cte.valid_to ");
        sb.append(") ");
        sb.append("SELECT variation_id, uuid, MIN(valid_from) AS valid_from, MAX(valid_to) AS valid_to ");
        sb.append("FROM cte ");
        sb.append("WHERE variation_id = ? ");
        sb.append("GROUP BY variation_id, uuid ");
        sb.append("ORDER BY valid_from");
        return jdbcTemplate.query(new String(sb),
                (resultSet, i) -> new SecurityTimespan(
                        resultSet.getString("isin"),
                        resultSet.getDate("valid_from"),
                        resultSet.getDate("valid_to")
                ), variationId);
    }
}
