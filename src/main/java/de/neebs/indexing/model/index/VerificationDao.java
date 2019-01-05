package de.neebs.indexing.model.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class VerificationDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

}
