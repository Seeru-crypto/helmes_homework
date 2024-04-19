TRUNCATE filter, user_filter cascade;
ALTER SEQUENCE filter_id_seq RESTART WITH 1;
ALTER SEQUENCE user_filter_id_seq RESTART WITH 1;

insert into user_filter (id, name, user_id)
VALUES (1, 'filter 1', '02438185-f1e4-4fd0-bc62-675f64b2c819');

insert into filter (criteria, type, value, field_name, user_filter_id)
VALUES ('CONTAINS', 'STRING', 'Does', 'name', 1)