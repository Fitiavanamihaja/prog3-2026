package mg.hei.kofia.data;

import mg.hei.kofia.db.DbConnection;
import mg.hei.kofia.model.Driver;
import mg.hei.kofia.model.LicenseCategory;
import mg.hei.kofia.model.Trip;
import mg.hei.kofia.model.TripStatus;
import mg.hei.kofia.model.Vehicle;
import mg.hei.kofia.model.VehicleType;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataRetriever {

    private final DbConnection dbConnection;

    public DataRetriever(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // ==================== Driver ====================

    public Driver findDriverById(String id) {
        String sql = "SELECT id, name, license_category, affiliation_date FROM driver WHERE id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapDriver(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC dans findDriverById", e);
        }
    }

    public List<Driver> findAllDrivers() {
        String sql = "SELECT id, name, license_category, affiliation_date FROM driver";
        List<Driver> drivers = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                drivers.add(mapDriver(rs));
            }
            return drivers;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC dans findAllDrivers", e);
        }
    }

    // ==================== Vehicle ====================

    public Vehicle findVehicleById(String id) {
        String sql = "SELECT id, plate_number, type, capacity_tons FROM vehicle WHERE id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapVehicle(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC dans findVehicleById", e);
        }
    }

    // ==================== Trip ====================

    public Trip findTripById(String id) {
        String sql = "SELECT id, driver_id, vehicle_id, trip_date, departure_city, arrival_city, "
                + "distance_km, billed_amount, status FROM trip WHERE id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapTrip(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC dans findTripById", e);
        }
    }

    public List<Trip> findTripsByDriver(String driverId) {
        String sql = "SELECT id, driver_id, vehicle_id, trip_date, departure_city, arrival_city, "
                + "distance_km, billed_amount, status FROM trip WHERE driver_id = ? ORDER BY trip_date";
        List<Trip> trips = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, driverId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    trips.add(mapTrip(rs));
                }
            }
            return trips;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC dans findTripsByDriver", e);
        }
    }

    public List<Trip> findTripsByPeriod(LocalDate from, LocalDate to) {
        String sql = "SELECT id, driver_id, vehicle_id, trip_date, departure_city, arrival_city, "
                + "distance_km, billed_amount, status FROM trip WHERE trip_date BETWEEN ? AND ? ORDER BY trip_date";
        List<Trip> trips = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    trips.add(mapTrip(rs));
                }
            }
            return trips;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC dans findTripsByPeriod", e);
        }
    }

    public List<Trip> findAllTrips() {
        String sql = "SELECT id, driver_id, vehicle_id, trip_date, departure_city, arrival_city, "
                + "distance_km, billed_amount, status FROM trip ORDER BY trip_date";
        List<Trip> trips = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                trips.add(mapTrip(rs));
            }
            return trips;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC dans findAllTrips", e);
        }
    }

    public Trip saveTrip(Trip trip) {
        boolean exists = findTripById(trip.getId()) != null;
        String sql = exists
                ? "UPDATE trip SET driver_id = ?, vehicle_id = ?, trip_date = ?, departure_city = ?, "
                    + "arrival_city = ?, distance_km = ?, billed_amount = ?, status = ? WHERE id = ?"
                : "INSERT INTO trip (driver_id, vehicle_id, trip_date, departure_city, arrival_city, "
                    + "distance_km, billed_amount, status, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, trip.getDriver().getId());
                ps.setString(2, trip.getVehicle().getId());
                ps.setDate(3, Date.valueOf(trip.getTripDate()));
                ps.setString(4, trip.getDepartureCity());
                ps.setString(5, trip.getArrivalCity());
                ps.setInt(6, trip.getDistanceKm());
                ps.setLong(7, trip.getBilledAmount());
                ps.setString(8, trip.getStatus().name());
                ps.setString(9, trip.getId());
                ps.executeUpdate();
                conn.commit();
                return trip;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC dans saveTrip", e);
        }
    }

    public Trip updateTripStatus(String tripId, TripStatus status) {
        String sql = "UPDATE trip SET status = ? WHERE id = ?";
        try (Connection conn = dbConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, status.name());
                ps.setString(2, tripId);
                int rows = ps.executeUpdate();
                if (rows == 0) {
                    conn.rollback();
                    return null;
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur JDBC dans updateTripStatus", e);
        }
        return findTripById(tripId);
    }

    // ==================== Mapping prive ====================

    private Driver mapDriver(ResultSet rs) throws SQLException {
        return new Driver(
                rs.getString("id"),
                rs.getString("name"),
                LicenseCategory.valueOf(rs.getString("license_category")),
                rs.getDate("affiliation_date").toLocalDate()
        );
    }

    private Vehicle mapVehicle(ResultSet rs) throws SQLException {
        return new Vehicle(
                rs.getString("id"),
                rs.getString("plate_number"),
                VehicleType.valueOf(rs.getString("type")),
                rs.getDouble("capacity_tons")
        );
    }

    private Trip mapTrip(ResultSet rs) throws SQLException {
        Driver driver = findDriverById(rs.getString("driver_id"));
        Vehicle vehicle = findVehicleById(rs.getString("vehicle_id"));
        return new Trip(
                rs.getString("id"),
                driver,
                vehicle,
                rs.getDate("trip_date").toLocalDate(),
                rs.getString("departure_city"),
                rs.getString("arrival_city"),
                rs.getInt("distance_km"),
                rs.getLong("billed_amount"),
                TripStatus.valueOf(rs.getString("status"))
        );
    }
}
