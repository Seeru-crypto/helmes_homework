BEGIN;

TRUNCATE TABLE sector cascade;
ALTER SEQUENCE sector_id_seq RESTART WITH 1;

INSERT INTO sector (name, parent_id, created_by, created_at, modified_by, modified_at, ID, value)
VALUES ('Manufacturing', NULL, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 1, 1),
       ('Construction materials', 1, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 2, 11),
       ('Electronics and Optics', 1, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 3, 12),

       ('Food and Beverage', 1, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 4, 13),
       ('Bakery & confectionery products', 4, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 5, 131),
       ('Beverages', 4, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 6, 132),
       ('Fish & fish products', 4, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 7, 133),
       ('Milk & dairy products', 4, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 8, 134),
       ('Other', 4, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 9, 135),
       ('Sweets & snack food', 4, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 10, 136),

       ('Furniture', 1, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 11, 14),
       ('Bathroom/sauna', 11, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 12, 141),
       ('Bedroom', 11, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 13, 142),
       ('Children’s room', 11, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 14, 143),
       ('Kitchen', 11, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 15, 144),
       ('Living room', 11, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 16, 145),
       ('Office', 11, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 17, 146),
       ('Other (Furniture)', 11, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 18, 147),
       ('Outdoor', 11, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 19, 148),
       ('Project furniture', 11, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 20, 149),

       ('Machinery', 1, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 21, 15),
       ('Machinery components', 21, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 22, 151),
       ('Machinery equipment/tools', 21, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 23, 152),
       ('Manufacture of machinery', 21, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 24, 153),
       ('Maritime', 21, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 25, 154),

       ('Aluminium and steel workboats', 25, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 26, 1541),
       ('Boat/Yacht building', 25, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 27, 1542),
       ('Ship repair and conversion', 25, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 28, 1543),

       ('Metal structures', 21, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 29, 155),
       ('Other', 21, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 30, 156),
       ('Repair and maintenance service', 21, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 31, 157),

       ('Metalworking', 1, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 32, 16),
       ('Construction of metal structures', 32, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 33, 161),
       ('Houses and buildings', 32, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 34, 162),
       ('Metal products', 32, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 35, 163),
       ('Metal works', 32, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 36, 164),

       ('CNC-machining', 36, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 37, 1641),
       ('Forgings, Fasteners', 36, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 38, 1642),
       ('Gas, Plasma, Laser cutting', 36, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 39, 1643),
       ('MIG, TIG, Aluminum welding', 36, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 40, 1644),

       ('Plastic and Rubber', 1, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 41, 17),
       ('Packaging', 41, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 42, 171),
       ('Plastic goods', 41, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 43, 172),
       ('Plastic processing technology', 41, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 44, 173),

       ('Blowing', 44, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 45, 1731),
       ('Moulding', 44, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 46, 1732),
       ('Plastics welding and processing', 44, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 47, 1733),

       ('Plastic profiles', 41, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 48, 174),

       ('Printing', 1, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 49, 18),
       ('Advertising', 49, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 50, 181),
       ('Book/Periodicals printing', 49, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 51, 182),
       ('Labelling and packaging printing', 49, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 52, 183),
       ('Textile and Clothing', 1, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 53, 19),
       ('Clothing', 53, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 54, 191),
       ('Textile', 53, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 55, 192),

       ('Wood', 1, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 56, 20),
       ('Other (Wood)', 56, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 57, 201),
       ('Wooden building materials', 56, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 58, 202),
       ('Wooden houses', 56, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 59, 203),

       ('Other', NULL, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 60, 2),
       ('Creative industries', 60, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 61, 21),
       ('Energy technology', 60, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 62, 22),
       ('Environment', 60, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 63, 23),

       ('Service', NULL, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 64, 3),
       ('Business services', 64, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 65, 31),
       ('Engineering', 64, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 66, 32),
       ('Information Technology and Telecommunications', 64, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 67, 33),

       ('Data processing, Web portals, E-marketing', 68, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 68, 331),
       ('Programming, Consultancy', 68, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 69, 332),
       ('Software, Hardware', 68, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 70, 333),
       ('Telecommunications', 68, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 71, 334),

       ('Tourism', 64, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 72, 34),
       ('Translation services', 64, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 73, 35),
       ('Transport and Logistics', 64, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 74, 36),

       ('Air', 74, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 75, 361),
       ('Rail', 74, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 76, 362),
       ('Road', 74, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 77, 363),
       ('Water', 74, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 78, 364);



--     ('Service', NULL, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 2, 2),
--     ('Other', NULL, NULL, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, 3, 3),


END;