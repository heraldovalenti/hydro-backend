INSERT INTO hq_model (id, station_id,
					  h_limit, h_offset,
					  low_pass_factor, low_pass_offset, low_pass_exponent,
					  high_pass_factor,high_pass_offset,high_pass_exponent) VALUES
(1,35,   0.35,-2.69,   57.673,0,2.473,   58.566,-0.1, 1.6666), --Punilla
(2,36,   0.95,0,       18.096,0,3.6366,  48.008,-0.45,1.6666), --Medina
(3,38,   0.95,0.05,    82.334,0,1.6038,  0,0,1),               --Miraflores
(4,65,   1.4,-1.65,    82.849,0,1.66666, 45.345,0.6,1.6666),   --SNIH - La Maroma
(5,67,   0.95,-2.16,   18.096,0,3.6366,  48.008,-0.45,1.6666), --SNIH - Metán - Desembocadura
(6,75,   0.35,-2.59,   57.673,0,2.473,   58.566,-0.1,1.6666),  --SNIH - La Punilla
(7,68,   0,0,          55.661,0.4,1.6666,55.661,0.4,1.6666);   --SNIH - El Tunal