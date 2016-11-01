package au.com.auspost.smartspb.domain;

import org.apache.commons.lang.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

public class StreetPostingBox {
    private Integer id;
    private String imei;
    private TimeZone timezone;
    private String apiKey;
    private String prevApiKey;
    private List<Reading> readings = new ArrayList<>();
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

    public List<Reading> getReadings() {
        return readings;
    }

    public StreetPostingBox addReading(Reading reading) {
        readings.add(reading);
        return this;
    }

    public void setReadings(List<Reading> readings) {
        this.readings = readings;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
