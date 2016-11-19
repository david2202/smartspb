package au.com.auspost.smartspb.web.value;

import au.com.auspost.smartspb.domain.LatLong;

import java.math.BigDecimal;

public class LatLongVO {
    private BigDecimal lat;
    private BigDecimal lng;

    public LatLongVO(LatLong latLong) {
        this.lat = latLong.getLatitude();
        this.lng = latLong.getLongitude();
    }
    public LatLongVO(BigDecimal lat, BigDecimal lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public BigDecimal getLng() {
        return lng;
    }
}
