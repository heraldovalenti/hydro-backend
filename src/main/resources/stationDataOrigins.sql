--DELETE FROM station_data_origin;

--data origin: AES
INSERT INTO station_data_origin
(id,data_origin_id,dimension_id,station_id,external_station_id,default_unit_id) VALUES
(1, 1, 1, 27, 'Cachi', null), --dimension: nivel
(2, 1, 3, 47, 'Cachi', null), --dimension: lluvia

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
(14, 1, 2, 37, 'Tunal', 5), --dimension: caudal, default unit: m3/sec

(28, 1, 3, 11, 'Termoandes', 3) --dimension: lluvia, default unit: mm

(48, 1, 1, 28, 'Alemania', null), --dimension: nivel
(49, 1, 3, 33, 'Alemania', null); --dimension: lluvia

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
(27, 2, 3, 40, 'IROSAR45', 4), --dimension: lluvia
(36, 2, 3, 48, 'ISALTA13', 4); --dimension: lluvia

--data origin: INTA
INSERT INTO station_data_origin
(id,data_origin_id,dimension_id,station_id,external_station_id,default_unit_id) VALUES
(29, 3, 3, 29, '352', 3), --dimension: lluvia, default unit: mm
(30, 3, 3, 41, '488', 3), --dimension: lluvia, default unit: mm
(31, 3, 3, 42, '395', 3), --dimension: lluvia, default unit: mm
(32, 3, 3, 43, '415', 3), --dimension: lluvia, default unit: mm
(33, 3, 3, 44, '393', 3), --dimension: lluvia, default unit: mm
(34, 3, 3, 45, '348', 3), --dimension: lluvia, default unit: mm
(35, 3, 3, 46, '516', 3); --dimension: lluvia, default unit: mm

INSERT INTO station_data_origin
(id,data_origin_id,dimension_id,station_id,external_station_id,default_unit_id) VALUES
(37, 5, 3, 49, 'ema_bicentenario', 3), --dimension: lluvia, default unit: mm
(38, 5, 3, 50, 'ema_olleros', 3), --dimension: lluvia, default unit: mm
(39, 5, 3, 51, 'ema_spmf', 3), --dimension: lluvia, default unit: mm
(40, 5, 3, 52, 'ema_lavina', 3), --dimension: lluvia, default unit: mm
(41, 5, 3, 53, 'ema_desdeelsur', 3), --dimension: lluvia, default unit: mm

(42, 5, 3, 54, 'ema_yatasto', 3), --dimension: lluvia, default unit: mm
(43, 5, 3, 55, 'ema_cerrillos', 3), --dimension: lluvia, default unit: mm
(44, 5, 3, 56, 'ema_esca3167', 3), --dimension: lluvia, default unit: mm
(45, 5, 3, 57, 'ema_elgalpon', 3), --dimension: lluvia, default unit: mm
(46, 5, 3, 58, 'ema_rlerma', 3), --dimension: lluvia, default unit: mm
(47, 5, 3, 59, 'ema_etchart', 3); --dimension: lluvia, default unit: mm

-- last ID: 49 (Alemania - AES)