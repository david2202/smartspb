package au.com.auspost.smartspb.domain;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.hibernate.annotations.BatchSize;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Version;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

@Entity
public class StreetPostingBox {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String imei;
    private TimeZone timezone;

    private String apiKey;
    private String prevApiKey;
    private String address;
    private Integer postcode;

    @Embedded
    private LatLong latLong;

    @OneToMany(mappedBy = "streetPostingBox", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 20)
    @OrderBy("dateTime DESC")
    private List<Reading> readings = new ArrayList<>();

    @Version
    private Integer version;

    public StreetPostingBox newApiKey() {
        this.prevApiKey = apiKey;
        this.apiKey = UUID.randomUUID().toString();
        return this;
    }

    public boolean checkApiKey(String apiKey) {
        if (apiKey != null &&
                ObjectUtils.equals(getApiKey(), apiKey) ||
                ObjectUtils.equals(getPrevApiKey(), apiKey)) {
            return true;
        }
        return false;
    }

    public Reading getLatestReading() {
        if (CollectionUtils.isEmpty(getReadings())) {
            return null;
        } else {
            return getReadings().get(0);
        }
    }

    public void clearLatest() {
        for (Reading r:getReadings()) {
            if (r.isLatest()) {
                r.setLatest(true);
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public TimeZone getTimezone() {
        return timezone;
    }

    public void setTimezone(TimeZone timezone) {
        this.timezone = timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = TimeZone.getTimeZone(timezone);
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getPrevApiKey() {
        return prevApiKey;
    }

    public void setPrevApiKey(String prevApiKey) {
        this.prevApiKey = prevApiKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPostcode() {
        return postcode;
    }

    public void setPostcode(Integer postcode) {
        this.postcode = postcode;
    }

    public LatLong getLatLong() {
        return latLong;
    }

    public void setLatLong(LatLong latLong) {
        this.latLong = latLong;
    }

    public List<Reading> getReadings() {
        return Collections.unmodifiableList(readings);
    }

    public StreetPostingBox addReading(Reading reading) {
        if (readings == null) {
            readings = new ArrayList<>();
        }
        readings.add(reading);
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
