TRUNCATE sector, "user", user_sectors CASCADE;

ALTER SEQUENCE sector_id_seq RESTART WITH 1;
ALTER SEQUENCE user_sectors_id_seq RESTART WITH 1;

TRUNCATE filter_options cascade;
ALTER SEQUENCE filter_options_id_seq RESTART WITH 1;

insert into filter_options (id, field, field_type)
values (1, 'name', 'STRING'),
       (2, 'dob', 'DATE'),
       (3, 'height', 'NUMBER');
