package au.com.auspost.smartspb.domain;

import java.math.BigDecimal;

public class LatLong {
    private BigDecimal latitude;
    private BigDecimal longitude;

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
