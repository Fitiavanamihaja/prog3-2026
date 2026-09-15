package mg.hei.kofia.mapper;

import mg.hei.kofia.dto.RevenueDto;
import mg.hei.kofia.dto.StatisticsDto;
import mg.hei.kofia.dto.TopEarningDriverDto;
import mg.hei.kofia.model.Driver;
import org.springframework.stereotype.Component;

@Component
public class DriverMapper {

    public RevenueDto toRevenueDto(Driver driver, String from, String to, long revenue, long cooperativeFee) {
        return new RevenueDto(driver.getId(), driver.getName(), from, to, revenue, cooperativeFee);
    }

    public StatisticsDto toStatisticsDto(Driver driver, int completedTrips, int totalDistanceKm, double averagePricePerKm) {
        return new StatisticsDto(driver.getId(), driver.getName(), completedTrips, totalDistanceKm, averagePricePerKm);
    }

    public TopEarningDriverDto toTopEarningDto(Driver driver, long revenue) {
        return new TopEarningDriverDto(driver.getId(), driver.getName(), revenue);
    }
}
