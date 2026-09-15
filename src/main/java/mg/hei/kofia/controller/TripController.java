package mg.hei.kofia.controller;

import mg.hei.kofia.dto.StatusUpdateDto;
import mg.hei.kofia.dto.TripDto;
import mg.hei.kofia.dto.TripRequestDto;
import mg.hei.kofia.mapper.TripMapper;
import mg.hei.kofia.model.TripStatus;
import mg.hei.kofia.service.TripService;
import mg.hei.kofia.validator.TripValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TripController {

    private final TripService tripService;
    private final TripValidator tripValidator;
    private final TripMapper tripMapper;

    public TripController(TripService tripService, TripValidator tripValidator, TripMapper tripMapper) {
        this.tripService = tripService;
        this.tripValidator = tripValidator;
        this.tripMapper = tripMapper;
    }

    @GetMapping("/trips")
    public ResponseEntity<List<TripDto>> getTrips(
            @RequestParam(required = false) String driverId,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to) {

        LocalDate fromDate = tripValidator.parseOptionalDate(from, "from");
        LocalDate toDate = tripValidator.parseOptionalDate(to, "to");

        List<TripDto> trips = tripService.findTrips(driverId, fromDate, toDate).stream()
                .map(tripMapper::toTripDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(trips);
    }

    @PutMapping("/trips/{tripId}")
    public ResponseEntity<TripDto> putTrip(@PathVariable String tripId, @RequestBody TripRequestDto body) {
        boolean exists = tripService.tripExists(tripId);
        var saved = tripService.createOrReplaceTrip(tripId, body);
        HttpStatus status = exists ? HttpStatus.OK : HttpStatus.CREATED;
        return ResponseEntity.status(status).body(tripMapper.toTripDto(saved));
    }

    @PutMapping("/trips/{tripId}/status")
    public ResponseEntity<TripDto> updateStatus(@PathVariable String tripId, @RequestBody StatusUpdateDto body) {
        TripStatus tripStatus = tripValidator.parseTripStatus(body);
        var updated = tripService.updateTripStatus(tripId, tripStatus);
        return ResponseEntity.ok(tripMapper.toTripDto(updated));
    }
}
