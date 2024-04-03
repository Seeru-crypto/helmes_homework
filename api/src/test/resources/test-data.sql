TRUNCATE sector, "user", user_sectors CASCADE;

ALTER SEQUENCE sector_id_seq RESTART WITH 1;
ALTER SEQUENCE user_sectors_id_seq RESTART WITH 1;
ALTER SEQUENCE user_id_seq RESTART WITH 1;
--
-- INSERT INTO sector (name, parent_id, created_by, created_at, modified_by, modified_at, ID, value)
-- VALUES ('Manufacturing', NULL, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 1, 1),
--        ('Construction materials', 1, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 2, 11),
--        ('Electronics and Optics', 1, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 3, 12);
