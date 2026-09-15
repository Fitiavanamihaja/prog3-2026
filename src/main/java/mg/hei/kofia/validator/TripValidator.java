package mg.hei.kofia.validator;

import mg.hei.kofia.dto.StatusUpdateDto;
import mg.hei.kofia.dto.TripRequestDto;
import mg.hei.kofia.exception.InvalidParameterException;
import mg.hei.kofia.model.TripStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TripValidator {

    public void validateTripRequest(TripRequestDto body) {
        if (body == null
                || body.getDriverId() == null || body.getDriverId().isBlank()
                || body.getVehicleId() == null || body.getVehicleId().isBlank()
                || body.getTripDate() == null || body.getTripDate().isBlank()
                || body.getDepartureCity() == null || body.getDepartureCity().isBlank()
                || body.getArrivalCity() == null || body.getArrivalCity().isBlank()
                || body.getDistanceKm() == null
                || body.getBilledAmount() == null) {
            throw new InvalidParameterException("Champs manquants dans le corps de la requete");
        }
    }

    public TripStatus parseTripStatus(StatusUpdateDto body) {
        if (body == null || body.getStatus() == null || body.getStatus().isBlank()) {
            throw new InvalidParameterException("Le champ 'status' est obligatoire");
        }
        try {
            return TripStatus.valueOf(body.getStatus());
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Statut invalide : " + body.getStatus());
        }
    }

    public LocalDate parseDate(String value, String paramName) {
        try {
            return LocalDate.parse(value);
        } catch (Exception e) {
            throw new InvalidParameterException("Format de date invalide pour " + paramName + " : " + value);
        }
    }

    public LocalDate parseOptionalDate(String value, String paramName) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return parseDate(value, paramName);
    }
}
