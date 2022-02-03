INSERT INTO hq_model (id, station_id,
					  h_limit, h_offset,
					  low_pass_factor, low_pass_offset, low_pass_exponent,
					  high_pass_factor,high_pass_offset,high_pass_exponent) VALUES
(1,35,   0.35,-2.26,   57.673,0,2.473,      58.566,-0.1, 1.6666), --Punilla
(2,36,   0.95,0.07,    22.892,0.00,2.5413,  40.297,-0.3, 1.6666), --Medina
(3,38,   0.95,0.00,    81.588,0,1.6014,     0,0,1),               --Miraflores
(4,65,   2.0,-0.05,    50.722,-1.65,1,      70.217,-1.55,1.6666), --SNIH - La Maroma
(5,67,   0.95,-2.15,   22.892,0.00,2.5413,  40.297,-0.3, 1.6666), --SNIH - Metán - Desembocadura
(6,75,   0.35,-2.26,   57.673,0,2.473,      58.566,-0.1, 1.6666), --SNIH - La Punilla
(7,68,   0,0,          55.661,0.4,1.6666,   55.661,0.4,1.6666),   --SNIH - El Tunal
(8,28,   0.1,0.11,     27.504,0.0,1.6666,   44.478,0.0,1.6666);   --AES - Alemania