package mg.hei.kofia.service;

import mg.hei.kofia.data.DataRetriever;
import mg.hei.kofia.dto.TripRequestDto;
import mg.hei.kofia.exception.InvalidParameterException;
import mg.hei.kofia.model.Driver;
import mg.hei.kofia.model.Trip;
import mg.hei.kofia.model.TripStatus;
import mg.hei.kofia.model.Vehicle;
import mg.hei.kofia.validator.TripValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripService {

    private final DataRetriever dataRetriever;
    private final TripValidator tripValidator;

    public TripService(DataRetriever dataRetriever, TripValidator tripValidator) {
        this.dataRetriever = dataRetriever;
        this.tripValidator = tripValidator;
    }

    public List<Trip> findTrips(String driverId, LocalDate from, LocalDate to) {
        return dataRetriever.findAllTrips().stream()
                .filter(t -> driverId == null || t.getDriver().getId().equals(driverId))
                .filter(t -> from == null || !t.getTripDate().isBefore(from))
                .filter(t -> to == null || !t.getTripDate().isAfter(to))
                .collect(Collectors.toList());
    }

    public Trip createOrReplaceTrip(String tripId, TripRequestDto body) {
        tripValidator.validateTripRequest(body);

        Driver driver = dataRetriever.findDriverById(body.getDriverId());
        if (driver == null) {
            throw new InvalidParameterException("driverId inconnu : " + body.getDriverId());
        }
        Vehicle vehicle = dataRetriever.findVehicleById(body.getVehicleId());
        if (vehicle == null) {
            throw new InvalidParameterException("vehicleId inconnu : " + body.getVehicleId());
        }

        LocalDate tripDate = tripValidator.parseDate(body.getTripDate(), "tripDate");

        Trip trip = new Trip(
                tripId,
                driver,
                vehicle,
                tripDate,
                body.getDepartureCity(),
                body.getArrivalCity(),
                body.getDistanceKm(),
                body.getBilledAmount(),
                TripStatus.COMPLETED
        );

        return dataRetriever.saveTrip(trip);
    }

    public boolean tripExists(String tripId) {
        return dataRetriever.findTripById(tripId) != null;
    }

    public Trip updateTripStatus(String tripId, TripStatus status) {
        Trip updated = dataRetriever.updateTripStatus(tripId, status);
        if (updated == null) {
            throw new mg.hei.kofia.exception.ResourceNotFoundException("Course introuvable : " + tripId);
        }
        return updated;
    }
}
