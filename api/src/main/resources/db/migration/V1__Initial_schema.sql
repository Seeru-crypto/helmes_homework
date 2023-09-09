BEGIN;

DROP TABLE IF EXISTS "user" CASCADE;

CREATE TABLE IF NOT EXISTS "user"
(
    id          BIGSERIAL NOT NULL PRIMARY KEY,
    name        CHARACTER VARYING COLLATE pg_catalog."default",
    agree_terms BOOLEAN,
    created_by  TEXT,
    created_at  TIMESTAMP,
    modified_by TEXT,
    modified_at TIMESTAMP
);

CREATE TABLE sector
(
    id          bigserial NOT NULL PRIMARY KEY,
    name        text      NOT NULL,
    parent_id   bigint,
    created_by  TEXT,
    created_at  TIMESTAMP,
    modified_by TEXT,
    modified_at TIMESTAMP
);

CREATE TABLE user_sectors
(
    id          bigserial NOT NULL PRIMARY KEY,
    user_id     bigint    NOT NULL,
    sector_id   bigint    NOT NULL,
    created_by  TEXT,
    created_at  TIMESTAMP,
    modified_by TEXT,
    modified_at TIMESTAMP,
    CONSTRAINT user_has_sectors FOREIGN KEY (user_id) REFERENCES "user" (id),
    CONSTRAINT sectors_have_users FOREIGN KEY (sector_id) REFERENCES sector (id)
);

END;