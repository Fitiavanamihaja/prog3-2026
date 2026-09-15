package mg.hei.kofia.service;

import mg.hei.kofia.data.DataRetriever;
import mg.hei.kofia.model.Driver;
import mg.hei.kofia.model.Trip;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RevenueService {

    private static final double COOPERATIVE_RATE = 0.15;

    private final DataRetriever dataRetriever;

    public RevenueService(DataRetriever dataRetriever) {
        this.dataRetriever = dataRetriever;
    }

    public long computeDriverRevenue(String driverId, LocalDate from, LocalDate to) {
        List<Trip> trips = dataRetriever.findTripsByDriver(driverId);
        long total = 0L;
        for (Trip trip : trips) {
            if (trip.isBillable() && isWithinPeriod(trip.getTripDate(), from, to)) {
                total += trip.getBilledAmount();
            }
        }
        return total;
    }

    public long computeCooperativeFee(String driverId, LocalDate from, LocalDate to) {
        long revenue = computeDriverRevenue(driverId, from, to);
        return Math.round(revenue * COOPERATIVE_RATE);
    }

    public double computeAveragePricePerKm(String driverId) {
        List<Trip> trips = dataRetriever.findTripsByDriver(driverId);
        long totalAmount = 0L;
        long totalDistance = 0L;
        for (Trip trip : trips) {
            if (trip.isBillable()) {
                totalAmount += trip.getBilledAmount();
                totalDistance += trip.getDistanceKm();
            }
        }
        if (totalDistance == 0) {
            return 0.0;
        }
        return (double) totalAmount / totalDistance;
    }

    public Driver findTopEarningDriver(LocalDate from, LocalDate to) {
        List<Driver> drivers = dataRetriever.findAllDrivers();
        Driver topDriver = null;
        long topRevenue = -1L;
        for (Driver driver : drivers) {
            long revenue = computeDriverRevenue(driver.getId(), from, to);
            if (revenue > topRevenue) {
                topRevenue = revenue;
                topDriver = driver;
            }
        }
        return topDriver;
    }

    private boolean isWithinPeriod(LocalDate date, LocalDate from, LocalDate to) {
        return !date.isBefore(from) && !date.isAfter(to);
    }
}
