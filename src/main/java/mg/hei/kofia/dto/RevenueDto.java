package mg.hei.kofia.dto;

public class RevenueDto {
    private String driverId;
    private String driverName;
    private String from;
    private String to;
    private long revenue;
    private long cooperativeFee;

    public RevenueDto() {
    }

    public RevenueDto(String driverId, String driverName, String from, String to, long revenue, long cooperativeFee) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.from = from;
        this.to = to;
        this.revenue = revenue;
        this.cooperativeFee = cooperativeFee;
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public long getCooperativeFee() {
        return cooperativeFee;
    }

    public void setCooperativeFee(long cooperativeFee) {
        this.cooperativeFee = cooperativeFee;
    }
}
