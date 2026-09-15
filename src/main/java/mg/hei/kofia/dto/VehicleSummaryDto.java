package mg.hei.kofia.dto;

public class VehicleSummaryDto {
    private String id;
    private String plateNumber;

    public VehicleSummaryDto() {
    }

    public VehicleSummaryDto(String id, String plateNumber) {
        this.id = id;
        this.plateNumber = plateNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
}
