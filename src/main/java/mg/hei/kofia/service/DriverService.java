package mg.hei.kofia.service;

import mg.hei.kofia.data.DataRetriever;
import mg.hei.kofia.dto.StatisticsDto;
import mg.hei.kofia.mapper.DriverMapper;
import mg.hei.kofia.model.Driver;
import mg.hei.kofia.model.Trip;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

    private final DataRetriever dataRetriever;
    private final RevenueService revenueService;
    private final DriverMapper driverMapper;

    public DriverService(DataRetriever dataRetriever, RevenueService revenueService, DriverMapper driverMapper) {
        this.dataRetriever = dataRetriever;
        this.revenueService = revenueService;
        this.driverMapper = driverMapper;
    }

    public StatisticsDto getStatistics(String driverId) {
        Driver driver = dataRetriever.findDriverById(driverId);

        List<Trip> completedTrips = dataRetriever.findTripsByDriver(driverId).stream()
                .filter(Trip::isBillable)
                .toList();

        int tripCount = completedTrips.size();
        int totalDistance = completedTrips.stream().mapToInt(Trip::getDistanceKm).sum();
        double avgPricePerKm = revenueService.computeAveragePricePerKm(driverId);

        return driverMapper.toStatisticsDto(driver, tripCount, totalDistance, avgPricePerKm);
    }
}
