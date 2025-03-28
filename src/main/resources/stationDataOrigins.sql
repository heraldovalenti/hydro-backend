--DELETE FROM station_data_origin;

--data origin: AES
INSERT INTO station_data_origin
(id,data_origin_id,dimension_id,station_id,external_station_id,default_unit_id) VALUES
(1, 1, 1, 27, 'Cachi', null), --dimension: nivel
(2, 1, 3, 47, 'Cachi', null), --dimension: lluvia

(76, 1, 1, 32, 'CampoQuijano', null), --dimension: nivel
(3,  1, 3, 13, 'CampoQuijano', null), --dimension: lluvia

(4, 1, 1, 38, 'Miraflores', 2), --dimension: nivel, default unit: m
(5, 1, 3, 15, 'Miraflores', null), --dimension: lluvia

(6, 1, 1, 36, 'Medina', null), --dimension: nivel
(7, 1, 3, 16, 'Medina', null), --dimension: lluvia

(8, 1, 1, 35, 'Punilla', null), --dimension: nivel
(9, 1, 3, 12, 'Punilla', null), --dimension: lluvia

(10, 1, 1, 39, 'CabraCorral', 2), --dimension: nivel, default unit: metro
(67, 1, 3, 6,  'CabraCorral', 3), --dimension: lluvia, default unit: mm

(11, 1, 2, 34, 'PenasBlancas', 5), --dimension: caudal, default unit: m3/sec
(12, 1, 1, 34, 'PenasBlancas', 2), --dimension: nivel, default unit: metro

(13, 1, 1, 37, 'Tunal', 2), --dimension: nivel, default unit: metro
(14, 1, 2, 37, 'Tunal', 5), --dimension: caudal, default unit: m3/sec
(68, 1, 3, 76, 'Tunal', 3), --dimension: lluvia, default unit: mm

(28, 1, 3, 11, 'Termoandes', 3) --dimension: lluvia, default unit: mm

(48, 1, 1, 28, 'Alemania', null), --dimension: nivel
(49, 1, 3, 33, 'Alemania', null), --dimension: lluvia

(82, 1, 3, 82, 'EscuelaMoldes', 3), --dimension: lluvia, default unit: mm

(104, 1, 3,  8, 'Carril', 3), --dimension: lluvia, default unit: mm
(105, 1, 3, 10, 'Metan', 3), --dimension: lluvia, default unit: mm

(116, 1, 1, 110, 'Viboras', null), --dimension: nivel
(117, 1, 3, 110, 'Viboras', null); --dimension: lluvia

--data origin: WeatherUnderground
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
(50, 2, 3, 60, 'ICAFAY1', 4), --dimension: lluvia
(77, 2, 3, 77, 'IMOLIN34', 4), --dimension: lluvia
(79, 2, 3, 79, 'ISALTA14', 4), --dimension: lluvia
(80, 2, 3, 80, 'ISALTA30', 4), --dimension: lluvia
(81, 2, 3, 81, 'ISALTA34', 4), --dimension: lluvia
(84, 2, 3, 83, 'ISALTA31', 4), --dimension: lluvia
(101, 2, 3, 100, 'ICAFAY2', 4), --dimension: lluvia
(102, 2, 3, 101, 'IROSAR87', 4), --dimension: lluvia
(103, 2, 3, 102, 'IGRLJOSD2', 4), --dimension: lluvia
(106, 2, 3, 103, 'ISALTA41', 4), --dimension: lluvia
(112, 2, 3, 107, 'ICOLAL3', 4), --dimension: lluvia
(118, 2, 3, 111, 'ICAMPO260', 4), --dimension: lluvia
(119, 2, 3, 112, 'ISALTA52', 4), --dimension: lluvia
(120, 2, 3, 113, 'ISALTA51', 4), --dimension: lluvia
(121, 2, 3, 114, 'IANTOF8', 4), --dimension: lluvia
(122, 2, 3, 115, 'ILASLA1', 4), --dimension: lluvia
(128, 2, 3, 120, 'ICERRI5', 4), --dimension: lluvia
(129, 2, 3, 121, 'ICAFAY3', 4), --dimension: lluvia
(130, 2, 3, 122, 'ILACALDE2', 4), --dimension: lluvia
(131, 2, 3, 123, 'ISALTA48', 4), --dimension: lluvia
(136, 2, 3, 126, 'IMETND2', 4); --dimension: lluvia

--data origin: INTA_Siga2
INSERT INTO station_data_origin
(id,data_origin_id,dimension_id,station_id,external_station_id,default_unit_id) VALUES
(29, 3, 3, 29, '352', 3), --dimension: lluvia, default unit: mm
(30, 3, 3, 41, '488', 3), --dimension: lluvia, default unit: mm
(31, 3, 3, 42, '395', 3), --dimension: lluvia, default unit: mm
(32, 3, 3, 43, '415', 3), --dimension: lluvia, default unit: mm
(33, 3, 3, 44, '393', 3), --dimension: lluvia, default unit: mm
(34, 3, 3, 45, '348', 3), --dimension: lluvia, default unit: mm
(35, 3, 3, 46, '516', 3), --dimension: lluvia, default unit: mm
(90, 3, 3, 89, '494', 3), --dimension: lluvia, default unit: mm
(91, 3, 3, 90, '369', 3), --dimension: lluvia, default unit: mm
(92, 3, 3, 91, '507', 3), --dimension: lluvia, default unit: mm
(93, 3, 3, 92, '414', 3), --dimension: lluvia, default unit: mm
(94, 3, 3, 93, '692', 3), --dimension: lluvia, default unit: mm
(95, 3, 3, 94,  '68', 3), --dimension: lluvia, default unit: mm
(96, 3, 3, 95, '496', 3); --dimension: lluvia, default unit: mm

--data origin: INTA_Anterior
INSERT INTO station_data_origin
(id,data_origin_id,dimension_id,station_id,external_station_id,default_unit_id) VALUES
(37, 5, 3, 49, 'ema_bicentenario', 3), --dimension: lluvia, default unit: mm
(38, 5, 3, 50, 'ema_olleros', 3), --dimension: lluvia, default unit: mm
(39, 5, 3, 51, 'eeasalta_elbrete', 3), --dimension: lluvia, default unit: mm
(40, 5, 3, 52, 'eeasalta_lavina', 3), --dimension: lluvia, default unit: mm
(41, 5, 3, 53, 'ema_desdeelsur', 3), --dimension: lluvia, default unit: mm

(42, 5, 3, 54, 'ema_yatasto', 3), --dimension: lluvia, default unit: mm
(43, 5, 3, 55, 'eeasalta_cerrillos', 3), --dimension: lluvia, default unit: mm
(44, 5, 3, 56, 'eeasalta_3167', 3), --dimension: lluvia, default unit: mm
(45, 5, 3, 57, 'eeasalta_3169', 3), --dimension: lluvia, default unit: mm
(46, 5, 3, 58, 'ema_rlerma', 3), --dimension: lluvia, default unit: mm
(47, 5, 3, 59, 'ema_etchart', 3), --dimension: lluvia, default unit: mm
(135, 5, 3, 125, 'eeasalta_abrasol', 3); --dimension: lluvia, default unit: mm

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
(132, 6, 3, 3, 68, '10626'), --El Tunal'), --10626, dimension: lluvia, default unit: mm
(60, 6, 1, 2, 69, '10684'), --Canal Finca Agropecuaria I'), --10684, dimension: nivel, default unit: m
(61, 6, 1, 2, 70, '10686'), --Finca Agropecuaria'), --10686, dimension: nivel, default unit: m
(62, 6, 1, 2, 71, '10695'), --Juramento - El Quebrachal'), --10695, dimension: nivel, default unit: m
(63, 6, 1, 2, 72, '10696'), --Canal Liag - El Quebrachal'), --10696, dimension: nivel, default unit: m
(64, 6, 1, 2, 73, '10699'), --Canal Macapillo'), --10699, dimension: nivel, default unit: m
(65, 6, 1, 2, 74, '10815'), --Canal de Dios'), --10815, dimension: nivel, default unit: m
(66, 6, 1, 2, 75, '10611'), --La Punilla'); --10611, dimension: nivel, default unit: m
(113, 6, 3, 3, 108, '10709'), --Coropampa', 10709, dimension: lluvia, default unit: mm
(114, 6, 1, 2, 108, '10709'), --Coropampa', 10709, dimension: nivel, default unit: m
(133, 6, 1, 2, 124, '10210'), --Pie de Medano'), --10210, dimension: nivel, default unit: m
(134, 6, 3, 3, 124, '10210'); --Pie de Medano'), --10210, dimension: lluvia, default unit: mm

-- data origin: HQ Model
INSERT INTO station_data_origin
(id,data_origin_id,dimension_id,default_unit_id,station_id,external_station_id) VALUES
(69, 7, 2, 5, 35, ''), --Punilla
(70, 7, 2, 5, 36, ''), --Medina
(71, 7, 2, 5, 38, ''), --Miraflores
(72, 7, 2, 5, 65, ''), --SNIH - La Maroma
(73, 7, 2, 5, 67, ''), --SNIH - Metán - Desembocadura
(74, 7, 2, 5, 75, ''), --SNIH - La Punilla
(75, 7, 2, 5, 68, ''), --SNIH - El Tunal
(83, 7, 2, 5, 28, ''), --AES - Alemania
(123, 7, 2, 5, 110, ''); --AES - Viboras

-- data origin: Weatherlink
INSERT INTO station_data_origin
(id,data_origin_id,dimension_id,default_unit_id,station_id,external_station_id) VALUES
(85, 8, 3, 4, 84, 'bd59bd4c-cbec-40e1-a2d4-36f5feea3475'), --Weatherlink - ERA 01 - Las Tolas
(86, 8, 3, 4, 85, '6ae0d38c-0f2c-46b9-8b84-263174d9f837'), --Weatherlink - Escuela Coronel Moldes
(87, 8, 3, 4, 86, 'a9e1a996-c5ea-4ae1-a582-b46c64403c99'), --Weatherlink - EMA Norte - Pampa Energía CTG
(88, 8, 3, 4, 87, '70d018b1-cfae-4ca6-b9b2-598bcd796cb3'), --Weatherlink - EMA Sur - Pampa Energía CTG
(89, 8, 3, 4, 88, 'f0805dcb-4b23-4e81-9a73-5e31c5e212dc'), --Weatherlink - EMA Nitratos Austin

(97,  8, 3, 4, 96, '9c4e35eb-9121-4c27-acac-f0901d51c5db'), --Weatherlink - Metan
(98,  8, 3, 4, 97, '17d6d435-6e0f-439d-a252-a474ccaa9382'), --Weatherlink - PLR 02 Cerro
(99,  8, 3, 4, 98, '4a824103-5e7d-467d-b6bc-8434fce4c44a'), --Weatherlink - Martillo Este
(100, 8, 3, 4, 99, '6ba85f40-404a-4661-bcfe-93cbcac16031'), --Weatherlink - EMA - Esc. Agrot. Payogasta
(115, 8, 3, 4, 109, 'ae2d126e-8524-427c-8757-25197f533276'), --Weatherlink - El Carril

(124, 8, 3, 4, 116, '18e5098c-8f0c-48a7-99e9-562fd205f097'), --Weatherlink - AeroSoluciones
(125, 8, 3, 4, 117, 'fea8cc22-e36e-4e1f-996e-bae6b49a3c16'), --Weatherlink - Ema Telescopio
(126, 8, 3, 4, 118, '9fdda27a-1101-48d0-b3fd-827d9f2cdf35'), --Weatherlink - Process Plant
(127, 8, 3, 4, 119, 'd238511c-6c93-48fd-859a-788eaa751e42'), --Weatherlink - Well 19

(158, 8, 3, 4, 145, '7ee3725b-7f71-4997-b352-dcce9d22038e'), --Weatherlink - GL 12 - Campo Azul
(159, 8, 3, 4, 146, '5abda403-31c0-4f3a-864c-3b3d5530d095'), --Weatherlink - GL 15 - Finca Buenaventura
(160, 8, 3, 4, 147, 'd425063e-9483-4bad-8f6f-43a4c6c6ab4f'); --Weatherlink - Weatherlink - GL 05 - Pozo de la Espuela

-- data origin: AES IBU
INSERT INTO station_data_origin
(id,data_origin_id,dimension_id,default_unit_id,station_id,external_station_id) VALUES
(107, 9, 1, 2, 104, 'CCOR_NIVEL'),  --AES (IBU) - Cabra Corral - nivel
(108, 9, 1, 2, 105, 'PB_NIVEL'),    --AES (IBU) - PB           - nivel
(109, 9, 2, 5, 105, 'PB_CAUDAL'),   --AES (IBU) - PB           - caudal
(110, 9, 1, 2, 106, 'TUNA_NIVEL'),  --AES (IBU) - El Tunal     - nivel
(111, 9, 2, 5, 106, 'TUNA_CAUDAL'); --AES (IBU) - El Tunal     - caudal

-- data origin: Weather Cloud
INSERT INTO station_data_origin
(id,data_origin_id,dimension_id,default_unit_id,station_id,external_station_id) VALUES
(137, 10, 3, 3, 127, 'SASA'),        --Weather Cloud - Salta
(138, 10, 3, 3, 128, '1968239092'),  --Weather Cloud - ESTACION SALTA
(139, 10, 3, 3, 129, '5457430806'),  --Weather Cloud - Infomet
(140, 10, 3, 3, 130, '9341323576'),  --Weather Cloud - Encon Beach
(141, 10, 3, 3, 131, '8418199173'),  --Weather Cloud - WS Los Robles

(142, 10, 3, 3, 132, '9831848457'),  --Weather Cloud - EMA Roberto
(143, 10, 3, 3, 133, '7770552514'),  --Weather Cloud - daza
(144, 10, 3, 3, 134, '5992524282'),  --Weather Cloud - Tako yana

(145, 10, 3, 3, 135, 'SASJ'),        --Weather Cloud - Jujuy
(146, 10, 3, 3, 136, '1752089865'),  --Weather Cloud - Los Perales
(147, 10, 3, 3, 137, '3863394405'),  --Weather Cloud - Easy Weather Jujuy
(148, 10, 3, 3, 138, '5433158430');  --Weather Cloud - Dazayala

-- data origin: PWS Weather
INSERT INTO station_data_origin
(id,data_origin_id,dimension_id,default_unit_id,station_id,external_station_id) VALUES
(149, 11, 3, 3, 139, 'mid_f9219'), --PWS Weather - F9219 - Salta
(150, 11, 3, 3, 140, 'mid_sasa'),  --PWS Weather - SASA - General Alvarado
(151, 11, 3, 3, 141, 'sasa'),      --PWS Weather - SASA - Salta Airport
(152, 11, 3, 3, 142, 'mid_sasj'),  --PWS Weather - SASJ - La Union
(153, 11, 3, 3, 143, 'sasj');      --PWS Weather - SASJ - Jujuy Airport

-- data origin: RP5
INSERT INTO station_data_origin
(id,data_origin_id,dimension_id,default_unit_id,station_id,external_station_id) VALUES
(154, 12, 3, 3, 144, 'Archivo_de_tiempo_en_Salta_(aeropuerto)');      --RP5 - Salta (aeropuerto)

-- data origin: AES GSheet
INSERT INTO station_data_origin
(id,data_origin_id,dimension_id,default_unit_id,station_id,external_station_id) VALUES
(155, 13, 1, 2, 38,  'miraflores'), --AES GSheet - Miraflores
(156, 13, 1, 2, 28,  'alemania'),   --AES GSheet - Alemania
(157, 13, 1, 2, 32,  'quijano'),    --AES GSheet - Campo Quijano
(161, 13, 1, 2, 148, 'maroma'),     --AES GSheet - Maroma
(162, 13, 1, 2, 36,  'medina');     --AES GSheet - Medina

-- last ID: 162 (AES GSheet - Medina)
