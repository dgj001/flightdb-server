package my.flightdb.flightdbserver.service;

import my.flightdb.flightdbserver.model.FlightData;
import my.flightdb.flightdbserver.model.FlightDataResult;
import my.flightdb.flightdbserver.repository.FlightDataRepository;
import org.springframework.stereotype.Service;

@Service
public class FlightDataServiceImpl implements FlightDataService {
    FlightDataRepository flightDataRepository;

    public FlightDataServiceImpl(FlightDataRepository flightDataRepository) {
        this.flightDataRepository = flightDataRepository;
    }

    @Override
    public FlightDataResult findByFlightId(Long flightId, int everyN) {
        FlightDataResult result = new FlightDataResult();
        Iterable<FlightData> iterator = flightDataRepository.findByFlightId(flightId);
        double maxLat = -90.0;
        double minLat = 90.0;
        double maxLng = -180;
        double minLng = 180.0;
        int index = 0;
        for (FlightData record : iterator) {
            maxLat = Math.max(maxLat, record.getLatitude());
            minLat = Math.min(minLat, record.getLatitude());
            maxLng = Math.max(maxLng, record.getLongitude());
            minLng = Math.min(minLng, record.getLongitude());
            if ((index % everyN) == 0) {
                result.getRecords().add(record);
            }
            index++;
        }
        result.setNumRecords(result.getRecords().size());
        result.setMaxLat(maxLat);
        result.setMinLat(minLat);
        result.setMaxLng(maxLng);
        result.setMinLng(minLng);
        return result;
    }

    @Override
    public FlightData save(FlightData object) {
        return flightDataRepository.save(object);
    }
}
