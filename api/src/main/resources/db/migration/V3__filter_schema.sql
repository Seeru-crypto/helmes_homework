-- user_filter
DROP TABLE IF EXISTS user_filter CASCADE;

CREATE TABLE IF NOT EXISTS user_filter
(
    id           BIGSERIAL NOT NULL PRIMARY KEY,
    name         VARCHAR(50) NOT NULL,
    user_id      uuid NOT NULL,

    created_by   TEXT,
    created_at   TIMESTAMP,
    modified_by  TEXT,
    modified_at  TIMESTAMP,

    CONSTRAINT user_has_filters FOREIGN KEY (user_id) REFERENCES "user" (id)
);

-- filter
DROP TABLE IF EXISTS filter CASCADE;
CREATE TABLE IF NOT EXISTS filter
(
    id           BIGSERIAL NOT NULL PRIMARY KEY,
    criteria     VARCHAR(50) NOT NULL,
    type         VARCHAR(50) NOT NULL,
    value        VARCHAR(50) NOT NULL,
    user_filter_id    BIGINT,

    created_by   TEXT,
    created_at   TIMESTAMP,
    modified_by  TEXT,
    modified_at  TIMESTAMP,
    CONSTRAINT filter_belongs_to_user_filters FOREIGN KEY (user_filter_id) REFERENCES "user_filter" (id)
);