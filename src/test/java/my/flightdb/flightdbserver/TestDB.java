package my.flightdb.flightdbserver;

import lombok.extern.slf4j.Slf4j;
import my.flightdb.flightdbserver.model.Flight;
import my.flightdb.flightdbserver.model.FlightData;
import my.flightdb.flightdbserver.service.FlightDataService;
import my.flightdb.flightdbserver.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class TestDB {

    @Autowired
    FlightService flightService;

    @Autowired
    FlightDataService flightDataService;

    public static String TAIL1 = "XYZ01";
    public static String DEP1 = "KOSU";
    public static String ARR1 = "KOSU";
    public static final String TYPE1 = "Boeing 737";
    public static final LocalDateTime DATE1 = LocalDateTime.of(2007, 12, 31, 23, 59, 59);

    public static final String TAIL2 = "XYZ02";
    public static String DEP2 = "KORD";
    public static String ARR2 = "KCMH";
    public static final String TYPE2 = "Airbus 319";
    public static final LocalDateTime DATE2 = LocalDateTime.of(2016, 2, 29, 0, 0, 0);

    public static final String TAIL3 = "XYZ03";
    public static final String TYPE3 = "";
    public static final LocalDateTime DATE3 = LocalDateTime.of(1999, 1, 1, 12, 24, 36);

    public static Long flightId1;
    public static final double DATA_1_TIME_1 = 0.0;
    public static final double DATA_1_ALT_1 = 1.0;
    public static final double DATA_1_GS_1 = 2.0;
    public static final double DATA_1_LAT_1 = 3.0;
    public static final double DATA_1_LNG_1 = 4.0;
    public static final double DATA_1_TIME_2 = 10.0;
    public static final double DATA_1_ALT_2 = 11.0;
    public static final double DATA_1_GS_2 = 12.0;
    public static final double DATA_1_LAT_2 = 13.0;
    public static final double DATA_1_LNG_2 = 14.0;

    public static Long flightId2;
    public static final double DATA_2_TIME_1 = 20.0;
    public static final double DATA_2_ALT_1 = 21.0;
    public static final double DATA_2_GS_1 = 22.0;
    public static final double DATA_2_LAT_1 = 23.0;
    public static final double DATA_2_LNG_1 = 24.0;

    public void createSharedDB() {
        log.info("TestDB.createSharedDB");

        Flight f1 = new Flight();
        f1.setTailNumber(TAIL1);
        f1.setDepartureAirport(DEP1);
        f1.setArrivalAirport(ARR1);
        f1.setAircraftType(TYPE1);
        f1.setStartDateTime(DATE1);
        flightService.save(f1);

        flightId1 = f1.getId();

        FlightData data;

        data = new FlightData();
        data.setFlightId(flightId1);
        data.setTime(DATA_1_TIME_1);
        data.setAltitude(DATA_1_ALT_1);
        data.setGroundSpeed(DATA_1_GS_1);
        data.setLatitude(DATA_1_LAT_1);
        data.setLongitude(DATA_1_LNG_1);
        flightDataService.save(data);

        data = new FlightData();
        data.setFlightId(flightId1);
        data.setTime(DATA_1_TIME_2);
        data.setAltitude(DATA_1_ALT_2);
        data.setGroundSpeed(DATA_1_GS_2);
        data.setLatitude(DATA_1_LAT_2);
        data.setLongitude(DATA_1_LNG_2);
        flightDataService.save(data);

        Flight f2 = new Flight();
        f2.setTailNumber(TAIL2);
        f2.setDepartureAirport(DEP2);
        f2.setArrivalAirport(ARR2);
        f2.setAircraftType(TYPE2);
        f2.setStartDateTime(DATE2);
        flightService.save(f2);

        flightId2 = f2.getId();

        data = new FlightData();
        data.setFlightId(flightId2);
        data.setTime(DATA_2_TIME_1);
        data.setAltitude(DATA_2_ALT_1);
        data.setGroundSpeed(DATA_2_GS_1);
        data.setLatitude(DATA_2_LAT_1);
        data.setLongitude(DATA_2_LNG_1);
        flightDataService.save(data);

        Flight f3 = new Flight();
        f3.setTailNumber(TAIL3);
        f3.setAircraftType(TYPE3);
        f3.setStartDateTime(DATE3);
        flightService.save(f3);
    }
}
