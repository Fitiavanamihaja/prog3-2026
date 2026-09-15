package mg.hei.kofia.dto;

public class TopEarningDriverDto {
    private String driverId;
    private String driverName;
    private long revenue;

    public TopEarningDriverDto() {
    }

    public TopEarningDriverDto(String driverId, String driverName, long revenue) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.revenue = revenue;
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

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }
}
