INSERT INTO hq_model (id, station_id,
					  h_limit, h_offset,
					  low_pass_factor, low_pass_offset, low_pass_exponent,
					  high_pass_factor,high_pass_offset,high_pass_exponent) VALUES
(1,35,   0.35,-2.15,   57.673,0,2.473,      58.566,-0.1, 1.6666), --Punilla
(2,36,   0.95,-0.41,   27.9  ,0.00,3.0843,  44.303,-0.3, 1.6667), --Medina
(3,38,   0,0,          81.588,0,1.6014,     81.588,0,1.6014    ), --Miraflores
(4,65,   1.4,-0.44,    10.329,0,1.6667,     48.917,-0.8,1.6667 ), --SNIH - La Maroma
(5,67,   0.95,-2.15,   22.892,0.00,2.5413,  40.297,-0.3, 1.6666), --SNIH - Metán - Desembocadura
(6,75,   0.35,-2.55,   57.673,0,2.473,      58.566,-0.1, 1.6666), --SNIH - La Punilla
(7,68,   0,0,          55.661,0.4,1.6666,   55.661,0.4,1.6666  ), --SNIH - El Tunal
(8,28,   0.1,0.03,     41.746,0,1.6667,     64.985,0,1.6667    ), --AES - Alemania
(9,110,  0.1,0,        19.995,0,1.6667,     16.194,0.1,1.666   ); --AES - Viboras