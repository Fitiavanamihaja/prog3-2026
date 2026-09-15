package mg.hei.kofia.model;

import java.util.Objects;

public class Vehicle {

    private String id;
    private String plateNumber;
    private VehicleType type;
    private double capacityTons;

    public Vehicle() {
    }

    public Vehicle(String id, String plateNumber, VehicleType type, double capacityTons) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.type = type;
        this.capacityTons = capacityTons;
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

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public double getCapacityTons() {
        return capacityTons;
    }

    public void setCapacityTons(double capacityTons) {
        this.capacityTons = capacityTons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
