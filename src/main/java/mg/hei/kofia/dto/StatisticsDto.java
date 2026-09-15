package mg.hei.kofia.dto;

public class StatisticsDto {
    private String driverId;
    private String driverName;
    private int completedTrips;
    private int totalDistanceKm;
    private double averagePricePerKm;

    public StatisticsDto() {
    }

    public StatisticsDto(String driverId, String driverName, int completedTrips, int totalDistanceKm,
                          double averagePricePerKm) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.completedTrips = completedTrips;
        this.totalDistanceKm = totalDistanceKm;
        this.averagePricePerKm = averagePricePerKm;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public int getCompletedTrips() {
        return completedTrips;
    }

    public void setCompletedTrips(int completedTrips) {
        this.completedTrips = completedTrips;
    }

    public int getTotalDistanceKm() {
        return totalDistanceKm;
    }

    public void setTotalDistanceKm(int totalDistanceKm) {
        this.totalDistanceKm = totalDistanceKm;
    }

    public double getAveragePricePerKm() {
        return averagePricePerKm;
    }

    public void setAveragePricePerKm(double averagePricePerKm) {
        this.averagePricePerKm = averagePricePerKm;
    }
}
