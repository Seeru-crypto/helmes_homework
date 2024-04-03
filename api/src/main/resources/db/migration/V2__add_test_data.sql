BEGIN;

TRUNCATE TABLE sector cascade;
ALTER SEQUENCE sector_id_seq RESTART WITH 1;

INSERT INTO sector (name, parent_id, created_by, created_at, modified_by, modified_at)
VALUES ('Manufacturing', NULL, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
       ('Construction materials', 1, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
       ('Electronics and optics', 1, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
       ('Food and Beverage', 1, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
       ('bakery & confectionery products', 4, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
       ('Beverages', 4, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
       ('Fish & fish products', 4, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
       ('Furniture', 1, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
       ('Bathroom/sauna', 8, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
       ('Bedroom', 8, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
       ('Children''s room', 8, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
       ('Service', NULL, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
       ('Business services', 12, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
       ('Engineering', 12, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
       ('Telecommunications', 12, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

END;