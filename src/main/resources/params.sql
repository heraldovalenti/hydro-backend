DELETE FROM data_origin;
DELETE FROM measurement_dimension;
DELETE FROM measurement_unit;

INSERT INTO data_origin (id, description) VALUES
(1, 'AES'),
(2, 'WeatherUnderground'),
(3, 'INTA'),
(4, 'SMG');

INSERT INTO measurement_dimension (id, description, preferred_unit_id) VALUES
(1, 'nivel', 2),
(2, 'caudal', 5),
(3, 'lluvia', 3),
(4, 'bateria', 1);

INSERT INTO measurement_unit (id, description, alias) VALUES
(1, 'voltage', 'V'),
(2, 'metro', 'm'),
(3, 'milimetro', 'mm'),
(4, 'pulgada', 'in'),
(5, 'm3/sec', 'm3/sec');
