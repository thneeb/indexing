CREATE TABLE hibernate_sequences (
    sequence_name VARCHAR(255) NOT NULL,
    next_val BIGINT(20),
    PRIMARY KEY (sequence_name)
);
