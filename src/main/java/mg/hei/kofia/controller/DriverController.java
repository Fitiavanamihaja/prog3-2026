package mg.hei.kofia.controller;

import mg.hei.kofia.data.DataRetriever;
import mg.hei.kofia.dto.RevenueDto;
import mg.hei.kofia.dto.StatisticsDto;
import mg.hei.kofia.exception.ResourceNotFoundException;
import mg.hei.kofia.mapper.DriverMapper;
import mg.hei.kofia.model.Driver;
import mg.hei.kofia.service.DriverService;
import mg.hei.kofia.service.RevenueService;
import mg.hei.kofia.validator.TripValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class DriverController {

    private final DataRetriever dataRetriever;
    private final RevenueService revenueService;
    private final DriverService driverService;
    private final DriverMapper driverMapper;
    private final TripValidator tripValidator;

    public DriverController(DataRetriever dataRetriever, RevenueService revenueService,
                            DriverService driverService, DriverMapper driverMapper,
                            TripValidator tripValidator) {
        this.dataRetriever = dataRetriever;
        this.revenueService = revenueService;
        this.driverService = driverService;
        this.driverMapper = driverMapper;
        this.tripValidator = tripValidator;
    }

    @GetMapping("/drivers/{driverId}/revenue")
    public ResponseEntity<RevenueDto> getRevenue(
            @PathVariable String driverId,
            @RequestParam String from,
            @RequestParam String to) {

        Driver driver = requireDriver(driverId);

        LocalDate fromDate = tripValidator.parseDate(from, "from");
        LocalDate toDate = tripValidator.parseDate(to, "to");

        long revenue = revenueService.computeDriverRevenue(driverId, fromDate, toDate);
        long fee = revenueService.computeCooperativeFee(driverId, fromDate, toDate);

        return ResponseEntity.ok(driverMapper.toRevenueDto(driver, from, to, revenue, fee));
    }

    @GetMapping("/drivers/{driverId}/statistics")
    public ResponseEntity<StatisticsDto> getStatistics(@PathVariable String driverId) {
        requireDriver(driverId);
        return ResponseEntity.ok(driverService.getStatistics(driverId));
    }

    private Driver requireDriver(String driverId) {
        Driver driver = dataRetriever.findDriverById(driverId);
        if (driver == null) {
            throw new ResourceNotFoundException("Chauffeur introuvable : " + driverId);
        }
        return driver;
    }
}
