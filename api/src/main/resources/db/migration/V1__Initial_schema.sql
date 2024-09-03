DROP TABLE IF EXISTS "user" CASCADE;

CREATE TABLE IF NOT EXISTS "user"
(
    id           UUID NOT NULL PRIMARY KEY,
    name         CHARACTER VARYING COLLATE pg_catalog."default",
    agree_terms  BOOLEAN,
    email        VARCHAR(50),
    dob          TIMESTAMP,
    height       int,
    phone_number VARCHAR(100) UNIQUE,
    created_by   TEXT,
    created_at   TIMESTAMP,
    modified_by  TEXT,
    modified_at  TIMESTAMP
);

DROP TABLE IF EXISTS sector CASCADE;

CREATE TABLE sector
(
    id          bigserial NOT NULL PRIMARY KEY,
    name        text      NOT NULL,
    value       int       NOT NULL unique,
    parent_id   bigint,
    created_by  TEXT,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    modified_by TEXT,
    modified_at TIMESTAMP NOT NULL DEFAULT NOW()
);

DROP TABLE IF EXISTS user_sectors CASCADE;

CREATE TABLE user_sectors
(
    id        bigserial NOT NULL PRIMARY KEY,
    user_id   uuid    NOT NULL,
    sector_id bigint    NOT NULL,
    CONSTRAINT user_has_sectors FOREIGN KEY (user_id) REFERENCES "user" (id),
    CONSTRAINT sectors_have_users FOREIGN KEY (sector_id) REFERENCES sector (id)
);