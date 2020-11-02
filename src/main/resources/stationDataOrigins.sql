--DELETE FROM station_data_origin;

--data origin: AES
INSERT INTO station_data_origin
(id,data_origin_id,dimension_id,station_id,external_station_id,default_unit_id) VALUES
(1, 1, 1, 27, 'Cachi', null), --dimension: nivel
(2, 1, 3, 27, 'Cachi', null), --dimension: lluvia

(3, 1, 3, 13, 'CampoQuijano', null), --dimension: lluvia

(4, 1, 1, 38, 'Miraflores', null), --dimension: nivel
(5, 1, 3, 15, 'Miraflores', null), --dimension: lluvia

(6, 1, 1, 36, 'Medina', null), --dimension: nivel
(7, 1, 3, 16, 'Medina', null), --dimension: lluvia

(8, 1, 1, 35, 'Punilla', null), --dimension: nivel
(9, 1, 3, 12, 'Punilla', null), --dimension: lluvia

(10, 1, 1, 39, 'CabraCorral', 2), --dimension: nivel, default unit: metro
(11, 1, 2, 39, 'CabraCorral', 5), --dimension: caudal, default unit: m3/sec

(12, 1, 1, 34, 'PenasBlancas', 2), --dimension: nivel, default unit: metro

(13, 1, 1, 37, 'Tunal', 2), --dimension: nivel, default unit: metro
(14, 1, 2, 37, 'Tunal', 5); --dimension: caudal, default unit: m3/sec

--data origin: WeatherUndeground
INSERT INTO station_data_origin
(id,data_origin_id,dimension_id,station_id,external_station_id,default_unit_id) VALUES
(15, 2, 3, 19, 'IROSARIO12', 4), --dimension: lluvia
(16, 2, 3, 20, 'ISALTACE3', 4), --dimension: lluvia
(17, 2, 3, 18, 'IMETND1', 4), --dimension: lluvia
(18, 2, 3, 21, 'ISALTA10', 4), --dimension: lluvia
(19, 2, 3, 22, 'ISALTA17', 4), --dimension: lluvia
(20, 2, 3, 23, 'IROSARIO32', 4), --dimension: lluvia
(21, 2, 3, 24, 'ITUMBA11', 4), --dimension: lluvia
(22, 2, 3, 25, 'IMONTE19', 4), --dimension: lluvia
(23, 2, 3, 26, 'IANDAL3', 4), --dimension: lluvia
(24, 2, 3, 4,  'ISALTA7', 4), --dimension: lluvia
(25, 2, 3, 5,  'ILACAL8', 4), --dimension: lluvia
(26, 2, 3, 3,  'ISALTA11', 4), --dimension: lluvia
(27, 2, 3, 40, 'IROSAR45', 4); --dimension: lluvia
