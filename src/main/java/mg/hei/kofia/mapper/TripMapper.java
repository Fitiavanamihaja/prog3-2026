package mg.hei.kofia.mapper;

import mg.hei.kofia.dto.DriverSummaryDto;
import mg.hei.kofia.dto.TripDto;
import mg.hei.kofia.dto.VehicleSummaryDto;
import mg.hei.kofia.model.Driver;
import mg.hei.kofia.model.Trip;
import mg.hei.kofia.model.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class TripMapper {

    public TripDto toTripDto(Trip trip) {
        return new TripDto(
                trip.getId(),
                toDriverSummary(trip.getDriver()),
                toVehicleSummary(trip.getVehicle()),
                trip.getTripDate().toString(),
                trip.getDepartureCity(),
                trip.getArrivalCity(),
                trip.getDistanceKm(),
                trip.getBilledAmount(),
                trip.getStatus().name()
        );
    }

    public DriverSummaryDto toDriverSummary(Driver driver) {
        return new DriverSummaryDto(driver.getId(), driver.getName());
    }

    public VehicleSummaryDto toVehicleSummary(Vehicle vehicle) {
        return new VehicleSummaryDto(vehicle.getId(), vehicle.getPlateNumber());
    }
}
