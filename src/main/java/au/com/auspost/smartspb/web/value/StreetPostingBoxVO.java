package au.com.auspost.smartspb.web.value;

import au.com.auspost.smartspb.domain.RemoteConfiguration;
import au.com.auspost.smartspb.domain.StreetPostingBox;
import au.com.auspost.smartspb.domain.Temperature;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTimeZone;

import java.util.Properties;
import java.util.TimeZone;

import static au.com.auspost.smartspb.Constants.DATE_FORMAT;

public class StreetPostingBoxVO {
    private Integer id;
    private String imei;
    private Integer version;
    private String apiKey;
    private String address;
    private Integer postcode;
    private LatLongVO latLong;
    private String lastReadingDateTime;
    private Integer grams;
    private Integer totalGrams;
    private Integer articleCount;
    private Temperature degreesC;

    private Properties config;

    public StreetPostingBoxVO(StreetPostingBox streetPostingBox, String timeZone) {
        copy(streetPostingBox, timeZone);
        this.config = null;
    }

    public StreetPostingBoxVO(StreetPostingBox streetPostingBox, RemoteConfiguration remoteConfiguration, String timeZone) {
        copy(streetPostingBox, timeZone);
        this.config = remoteConfiguration.getPropertiesAsProperties();
    }

    private void copy(StreetPostingBox streetPostingBox, String timeZone) {
        this.id = streetPostingBox.getId();
        this.imei = streetPostingBox.getImei();
        this.version = streetPostingBox.getVersion();
        this.apiKey = streetPostingBox.getApiKey();
        this.address = streetPostingBox.getAddress();
        this.postcode = streetPostingBox.getPostcode();
        this.latLong = new LatLongVO(streetPostingBox.getLatLong());
        if (CollectionUtils.isEmpty(streetPostingBox.getReadings())) {
            this.grams = 0;
            this.totalGrams = 0;
            this.articleCount = 0;
        } else {
            if (timeZone == null) {
                this.lastReadingDateTime = streetPostingBox.getLatestReading().getDateTime().toString(DATE_FORMAT);
            } else {
                this.lastReadingDateTime = streetPostingBox.getLatestReading().getDateTime().toDateTime(DateTimeZone.forTimeZone(TimeZone.getTimeZone(timeZone))).toString(DATE_FORMAT);
            }
            this.grams = streetPostingBox.getLatestReading().getGrams();
            this.totalGrams = streetPostingBox.getLatestReading().getTotalGrams();
            this.articleCount = streetPostingBox.getLatestReading().getArticleCount();
            this.degreesC = streetPostingBox.getLatestReading().getDegreesC();
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

    public Integer getVersion() {
        return version;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getAddress() {
        return address;
    }

    public Integer getPostcode() {
        return postcode;
    }

    public LatLongVO getLatLong() {
        return latLong;
    }

    public String getLastReadingDateTime() {
        return lastReadingDateTime;
    }

    public Integer getGrams() {
        return grams;
    }

    public Integer getTotalGrams() {
        return totalGrams;
    }

    public Integer getArticleCount() {
        return articleCount;
    }

    public Temperature getDegreesC() {
        return degreesC;
    }

    public Properties getConfig() {
        return config;
    }
}
