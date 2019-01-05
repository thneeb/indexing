package de.neebs.indexing.model.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CalculateDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void deleteCalculateSecurity(int variationId, CalculationConfig calculationConfig) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM calc_security ");
        sb.append("WHERE name = ? ");
        sb.append("AND variation_id = ? ");
        sb.append("AND open_high_close_low = ?");
        if (calculationConfig.getCalculateFrom() != null) {
            sb.append(" the_date >= ?");
        }
        if (calculationConfig.getCalculateTo() != null) {
            sb.append(" the_date <= ?");
        }
        jdbcTemplate.update(new String(sb), preparedStatement -> {
            int i = 1;
            preparedStatement.setString(i++, calculationConfig.getName());
            preparedStatement.setInt(i++, variationId);
            preparedStatement.setString(i++, calculationConfig.getOpenHighCloseLow().name());
            if (calculationConfig.getCalculateFrom() != null) {
                preparedStatement.setDate(i++, new java.sql.Date(calculationConfig.getCalculateFrom().getTime()));
            }
            if (calculationConfig.getCalculateTo() != null) {
                preparedStatement.setDate(i, new java.sql.Date(calculationConfig.getCalculateTo().getTime()));
            }
        });
    }

    public void insertCalculateSecuritySimple(int variationId, CalculationConfig calculationConfig) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO calc_security ");
        sb.append("(SELECT ?, css.* FROM vw_calc_security_simple css ");
        sb.append("WHERE variation_id = ? ");
        sb.append("AND open_high_close_low = ?");
        if (calculationConfig.getCalculateFrom() != null) {
            sb.append(" the_date >= ?");
        }
        if (calculationConfig.getCalculateTo() != null) {
            sb.append(" the_date <= ?");
        }
        sb.append(")");
        jdbcTemplate.update(new String(sb), preparedStatement -> {
            int i = 1;
            preparedStatement.setString(i++, calculationConfig.getName());
            preparedStatement.setInt(i++, variationId);
            preparedStatement.setString(i++, calculationConfig.getOpenHighCloseLow().name());
            if (calculationConfig.getCalculateFrom() != null) {
                preparedStatement.setDate(i++, new java.sql.Date(calculationConfig.getCalculateFrom().getTime()));
            }
            if (calculationConfig.getCalculateTo() != null) {
                preparedStatement.setDate(i, new java.sql.Date(calculationConfig.getCalculateTo().getTime()));
            }
        });

    }

    public void deleteCalculateIndex(int variationId, CalculationConfig calculationConfig) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM calc_index ");
        sb.append("WHERE variation_id = ? ");
        sb.append("AND name = ? ");
        if (calculationConfig.getCalculateFrom() != null) {
            sb.append(" the_date >= ?");
        }
        if (calculationConfig.getCalculateTo() != null) {
            sb.append(" the_date <= ?");
        }
        jdbcTemplate.update(new String(sb), preparedStatement -> {
            int i = 1;
            preparedStatement.setInt(i++, variationId);
            preparedStatement.setString(i++, calculationConfig.getName());
            if (calculationConfig.getCalculateFrom() != null) {
                preparedStatement.setDate(i++, new java.sql.Date(calculationConfig.getCalculateFrom().getTime()));
            }
            if (calculationConfig.getCalculateTo() != null) {
                preparedStatement.setDate(i, new java.sql.Date(calculationConfig.getCalculateTo().getTime()));
            }
        });
    }

    public void insertCalculateIndex(int variationId, CalculationConfig calculationConfig) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO calc_index (");
        sb.append("SELECT variation_id, name, the_date, SUM(price * exchange_rate * fraction) AS index_value ");
        sb.append("FROM calc_security ");
        sb.append("WHERE variation_id = ? ");
        sb.append("AND name = ? ");
        if (calculationConfig.getCalculateFrom() != null) {
            sb.append("the_date >= ? ");
        }
        if (calculationConfig.getCalculateTo() != null) {
            sb.append("the_date <= ? ");
        }
        sb.append("GROUP BY variation_id, name, the_date");
        sb.append(")");
        jdbcTemplate.update(new String(sb), preparedStatement -> {
            int i = 1;
            preparedStatement.setInt(i++, variationId);
            preparedStatement.setString(i++, calculationConfig.getName());
            if (calculationConfig.getCalculateFrom() != null) {
                preparedStatement.setDate(i++, new java.sql.Date(calculationConfig.getCalculateFrom().getTime()));
            }
            if (calculationConfig.getCalculateTo() != null) {
                preparedStatement.setDate(i, new java.sql.Date(calculationConfig.getCalculateTo().getTime()));
            }
        });
    }
}
