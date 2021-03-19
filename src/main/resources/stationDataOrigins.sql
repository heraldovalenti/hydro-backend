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
(67, 1, 3, 6,  'CabraCorral', 3), --dimension: lluvia, default unit: mm

(12, 1, 1, 34, 'PenasBlancas', 2), --dimension: nivel, default unit: metro

(13, 1, 1, 37, 'Tunal', 2), --dimension: nivel, default unit: metro
(14, 1, 2, 37, 'Tunal', 5), --dimension: caudal, default unit: m3/sec
(68, 1, 3, 76, 'Tunal', 3), --dimension: lluvia, default unit: mm

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
(36, 2, 3, 48, 'ISALTA13', 4), --dimension: lluvia
(50, 2, 3, 60, 'ICAFAY1', 4); --dimension: lluvia

--data origin: INTA_Siga2
INSERT INTO station_data_origin
(id,data_origin_id,dimension_id,station_id,external_station_id,default_unit_id) VALUES
(29, 3, 3, 29, '352', 3), --dimension: lluvia, default unit: mm
(30, 3, 3, 41, '488', 3), --dimension: lluvia, default unit: mm
(31, 3, 3, 42, '395', 3), --dimension: lluvia, default unit: mm
(32, 3, 3, 43, '415', 3), --dimension: lluvia, default unit: mm
(33, 3, 3, 44, '393', 3), --dimension: lluvia, default unit: mm
(34, 3, 3, 45, '348', 3), --dimension: lluvia, default unit: mm
(35, 3, 3, 46, '516', 3); --dimension: lluvia, default unit: mm

--data origin: INTA_Anterior
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

-- data origin: SNIH
INSERT INTO station_data_origin
(id,data_origin_id,dimension_id,default_unit_id,station_id,external_station_id) VALUES
(51, 6, 3, 3, 61, '10687'), --Güemes', 10687, dimension: lluvia, default unit: mm
(52, 6, 1, 2, 61, '10687'), --Güemes', 10687, dimension: nivel, default unit: m
(53, 6, 1, 2, 62, '10036'), --Fraile Pintado - RN 34', --10036, dimension: nivel, default unit: m
(54, 6, 1, 2, 63, '10646'), --Cachi', --10646, dimension: nivel, default unit: m
(55, 6, 1, 2, 64, '10701'), --El Encon'), --10701, dimension: nivel, default unit: m
(56, 6, 1, 2, 65, '10706'), --La Maroma'), --10706, dimension: nivel, default unit: m
(57, 6, 1, 2, 66, '10622'), --Cabra Corral'), --10622, dimension: nivel, default unit: m
(58, 6, 1, 2, 67, '10705'), --Metán - Desembocadura'), --10705, dimension: nivel, default unit: m
(59, 6, 1, 2, 68, '10626'), --El Tunal'), --10626, dimension: nivel, default unit: m
(60, 6, 1, 2, 69, '10684'), --Canal Finca Agropecuaria I'), --10684, dimension: nivel, default unit: m
(61, 6, 1, 2, 70, '10686'), --Finca Agropecuaria'), --10686, dimension: nivel, default unit: m
(62, 6, 1, 2, 71, '10695'), --Juramento - El Quebrachal'), --10695, dimension: nivel, default unit: m
(63, 6, 1, 2, 72, '10696'), --Canal Liag - El Quebrachal'), --10696, dimension: nivel, default unit: m
(64, 6, 1, 2, 73, '10699'), --Canal Macapillo'), --10699, dimension: nivel, default unit: m
(65, 6, 1, 2, 74, '10815'), --Canal de Dios'), --10815, dimension: nivel, default unit: m
(66, 6, 1, 2, 75, '10611'); --La Punilla'); --10611, dimension: nivel, default unit: m

-- last ID: 68 (Tunal)