package mg.hei.kofia.model;

import java.time.LocalDate;
import java.util.Objects;

public class Driver {

    private String id;
    private String name;
    private LicenseCategory licenseCategory;
    private LocalDate affiliationDate;

    public Driver() {
    }

    public Driver(String id, String name, LicenseCategory licenseCategory, LocalDate affiliationDate) {
        this.id = id;
        this.name = name;
        this.licenseCategory = licenseCategory;
        this.affiliationDate = affiliationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LicenseCategory getLicenseCategory() {
        return licenseCategory;
    }

    public void setLicenseCategory(LicenseCategory licenseCategory) {
        this.licenseCategory = licenseCategory;
    }

    public LocalDate getAffiliationDate() {
        return affiliationDate;
    }

    public void setAffiliationDate(LocalDate affiliationDate) {
        this.affiliationDate = affiliationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Driver)) return false;
        Driver driver = (Driver) o;
        return Objects.equals(id, driver.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
