INSERT INTO weather_station (city, station) VALUES ('Tallinn', 'Tallinn-Harku');
INSERT INTO weather_station (city, station) VALUES ('Tartu', 'Tartu-Tõravere');
INSERT INTO weather_station (city, station) VALUES ('Pärnu', 'Pärnu');
INSERT INTO regional_base_fee (city, car_fee, scooter_fee, bike_fee) VALUES ('Tallinn', 4, 3.5, 3);
INSERT INTO regional_base_fee (city, car_fee, scooter_fee, bike_fee) VALUES ('Tartu', 3.5, 3, 2.5);
INSERT INTO regional_base_fee (city, car_fee, scooter_fee, bike_fee) VALUES ('Pärnu', 3, 2.5, 2);
INSERT INTO weather_fees (atef_less_than_minus_ten, atef_minus_ten_to_zero, wsef_ten_to_twenty_mps, wpef_snow_or_sleet, wpef_rain) VALUES (1, 0.5, 0.5, 1, 0.5);