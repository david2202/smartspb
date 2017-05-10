package au.com.auspost.smartspb.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class LatLong {
    private BigDecimal latitude;
    private BigDecimal longitude;

    public LatLong() {
        // Needed for Hibernate
    }

    public LatLong(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }
}
