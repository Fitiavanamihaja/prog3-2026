package mg.hei.kofia.controller;

import mg.hei.kofia.dto.TopEarningDriverDto;
import mg.hei.kofia.exception.ResourceNotFoundException;
import mg.hei.kofia.mapper.DriverMapper;
import mg.hei.kofia.model.Driver;
import mg.hei.kofia.service.RevenueService;
import mg.hei.kofia.validator.TripValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class StatisticsController {

    private final RevenueService revenueService;
    private final DriverMapper driverMapper;
    private final TripValidator tripValidator;

    public StatisticsController(RevenueService revenueService, DriverMapper driverMapper,
                                TripValidator tripValidator) {
        this.revenueService = revenueService;
        this.driverMapper = driverMapper;
        this.tripValidator = tripValidator;
    }

    @GetMapping("/statistics/top-earning-driver")
    public ResponseEntity<TopEarningDriverDto> getTopEarningDriver(
            @RequestParam String from,
            @RequestParam String to) {

        LocalDate fromDate = tripValidator.parseDate(from, "from");
        LocalDate toDate = tripValidator.parseDate(to, "to");

        Driver top = revenueService.findTopEarningDriver(fromDate, toDate);
        if (top == null) {
            throw new ResourceNotFoundException("Aucun chauffeur enregistre");
        }

        long revenue = revenueService.computeDriverRevenue(top.getId(), fromDate, toDate);
        return ResponseEntity.ok(driverMapper.toTopEarningDto(top, revenue));
    }
}
