TRUNCATE sector, "user", user_sectors CASCADE;

ALTER SEQUENCE sector_id_seq RESTART WITH 1;
ALTER SEQUENCE user_sectors_id_seq RESTART WITH 1;
ALTER SEQUENCE user_id_seq RESTART WITH 1;