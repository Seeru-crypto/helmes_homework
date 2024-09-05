DROP TABLE IF EXISTS filter_options CASCADE;
DROP TYPE IF EXISTS FIELD_TYPE;

CREATE TYPE FIELD_TYPE AS ENUM ('STRING', 'DATE', 'NUMBER');

CREATE TABLE IF NOT EXISTS filter_options
(
    id          BIGSERIAL NOT NULL PRIMARY KEY,
    field       CHARACTER VARYING,
    field_type  FIELD_TYPE,

    created_by  TEXT,
    created_at  TIMESTAMP,
    modified_by TEXT,
    modified_at TIMESTAMP
);