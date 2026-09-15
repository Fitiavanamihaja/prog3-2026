package mg.hei.kofia.dto;

public class TripDto {
    private String id;
    private DriverSummaryDto driver;
    private VehicleSummaryDto vehicle;
    private String tripDate;
    private String departureCity;
    private String arrivalCity;
    private int distanceKm;
    private long billedAmount;
    private String status;

    public TripDto() {
    }

    public TripDto(String id, DriverSummaryDto driver, VehicleSummaryDto vehicle, String tripDate,
                    String departureCity, String arrivalCity, int distanceKm, long billedAmount, String status) {
        this.id = id;
        this.driver = driver;
        this.vehicle = vehicle;
        this.tripDate = tripDate;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.distanceKm = distanceKm;
        this.billedAmount = billedAmount;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DriverSummaryDto getDriver() {
        return driver;
    }

    public void setDriver(DriverSummaryDto driver) {
        this.driver = driver;
    }

    public VehicleSummaryDto getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleSummaryDto vehicle) {
        this.vehicle = vehicle;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public int getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(int distanceKm) {
        this.distanceKm = distanceKm;
    }

    public long getBilledAmount() {
        return billedAmount;
    }

    public void setBilledAmount(long billedAmount) {
        this.billedAmount = billedAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
