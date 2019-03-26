CREATE FUNCTION slc (lat1 double, lon1 double, lat2 double, lon2 double)
  RETURNS double
RETURN 6371 * acos(cos(radians(lat1)) * cos(radians(lat2)) * cos(radians(lon2) - radians(lon1)) + sin(radians(lat1)) * sin(radians(lat2)));

create table flight (
	id bigint not null auto_increment, 
	aircraft_type varchar(255), 
	arrival_airport varchar(255), 
	departure_airport varchar(255), 
	start_date_time datetime, 
	tail_number varchar(255), 
	primary key (id)
) engine=InnoDB;
	
create table flight_data (
	id bigint not null auto_increment, 
	altitude double precision not null, 
	course double precision not null, 
	flight_id bigint, 
	fuel_qty_1 double precision not null, 
	fuel_qty_2 double precision not null, 
	fuel_qty_3 double precision not null, 
	fuel_qty_4 double precision not null, 
	groundspeed double precision not null, 
	heading double precision not null, 
	lat_accel double precision not null, 
	latitude double precision not null, 
	lon_accel double precision not null, 
	longitude double precision not null, 
	phase integer not null, 
	pitch double precision not null, 
	roll double precision not null, 
	tas double precision not null, 
	time double precision not null, 
	verticalspeed double precision not null, 
	primary key (id)
) engine=InnoDB;

create table grouping (
	id bigint not null auto_increment, 
	count integer not null, 
	field_name varchar(255), 
	field_value varchar(255), 
	primary key (id)
) engine=InnoDB;


DROP PROCEDURE IF EXISTS compute_distinct_departure_counts;
DELIMITER //
CREATE FUNCTION compute_distinct_departure_counts(
	OUT output_count INT
)
BEGIN
	DECLARE cursor_finished INTEGER DEFAULT 0;
	DECLARE cursor_airport varchar(255) DEFAULT "";

	DECLARE departure_cursor CURSOR FOR 
	SELECT DISTINCT departure_airport FROM flight;

	DECLARE CONTINUE HANDLER 
		FOR NOT FOUND SET cursor_finished = 1;

	OPEN departure_cursor;

	get_count: LOOP
	
	FETCH departure_cursor INTO cursor_airport;
	
	IF cursor_finished = 1 THEN 
		LEAVE get_count;
	END IF;
	
	SELECT departure_airport, COUNT(departure_airport) 
	INTO computed_airport, count
	FROM flight WHERE departure_airport = cursor_airport;
	
	END LOOP get_count;
	
	CLOSE departure_cursor;
END //
DELIMITER ;

CALL compute_distinct_departure_counts();

SELECT
    f.departure_airport,
    count(*) as flight_count
FROM
    flight f
GROUP BY
    departure_airport
ORDER BY
	flight_count DESC
LIMIT 5

SELECT f.departure_airport, count(*) as flight_count FROM flight f 
GROUP BY departure_airport ORDER BY flight_count DESC
