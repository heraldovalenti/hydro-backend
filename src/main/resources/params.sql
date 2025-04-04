DELETE FROM data_origin;
DELETE FROM measurement_dimension;
DELETE FROM measurement_unit;

INSERT INTO data_origin (id, description) VALUES
(1, 'AES'),
(2, 'WeatherUnderground'),
(3, 'INTA_siga2'),
(4, 'SMG'),
(5, 'INTA_Anterior'),
(6, 'SNIH'),
(7, 'HQ_model'),
(8, 'Weatherlink'),
(9, 'AES (IBU)'),
(10, 'WeatherCloud'),
(11, 'PWSWeather'),
(12, 'RP5'),
(13, 'Google Sheet');

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
(5, 'm3/sec', 'm3/sec'),
(6, 'centimetro', 'cm');
