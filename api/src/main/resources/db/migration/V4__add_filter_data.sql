TRUNCATE filter, user_filter cascade;
ALTER SEQUENCE filter_id_seq RESTART WITH 1;
ALTER SEQUENCE user_filter_id_seq RESTART WITH 1;

insert into user_filter (id, name, user_id)
VALUES (1, 'filter 1', '02438185-f1e4-4fd0-bc62-675f64b2c819'),
       (2, 'filter 2', '02438185-f1e4-4fd0-bc62-675f64b2c819'),
       (3, 'filter 3', '02438185-f1e4-4fd0-bc62-675f64b2c820')
       ;

insert into filter (criteria, type, value, field_name, user_filter_id)
VALUES ('CONTAINS', 'STRING', 'Does', 'NAME', 1),
       ('DOES_NOT_CONTAIN','STRING', 'John', 'NAME', 1),
       ('BEFORE', 'DATE','2001-04-10T21:00:25.451157400Z', 'DOB', 3);