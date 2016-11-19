package au.com.auspost.smartspb.web.value;

import au.com.auspost.smartspb.domain.RemoteConfiguration;
import au.com.auspost.smartspb.domain.StreetPostingBox;
import au.com.auspost.smartspb.domain.Temperature;

import java.util.Properties;

public class StreetPostingBoxVO {
    private Integer id;
    private String imei;
    private Integer version;
    private String apiKey;
    private String address;
    private Integer postCode;
    private LatLongVO latLong;
    private Integer grams;
    private Integer totalGrams;
    private Temperature degreesC;

    private Properties config;

    public StreetPostingBoxVO(StreetPostingBox streetPostingBox) {
        copy(streetPostingBox);
        this.config = null;
    }

    public StreetPostingBoxVO(StreetPostingBox streetPostingBox, RemoteConfiguration remoteConfiguration) {
        copy(streetPostingBox);
        this.config = remoteConfiguration.getProperties();
    }

    private void copy(StreetPostingBox streetPostingBox) {
        this.id = streetPostingBox.getId();
        this.imei = streetPostingBox.getImei();
        this.version = streetPostingBox.getVersion();
        this.apiKey = streetPostingBox.getApiKey();
        this.address = streetPostingBox.getAddress();
        this.postCode = streetPostingBox.getPostCode();
        this.latLong = new LatLongVO(streetPostingBox.getLatLong());
        this.grams = streetPostingBox.getLatestReading().getGrams();
        this.totalGrams = streetPostingBox.getLatestReading().getTotalGrams();
        this.degreesC = streetPostingBox.getLatestReading().getDegreesC();
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

    public Integer getPostCode() {
        return postCode;
    }

    public LatLongVO getLatLong() {
        return latLong;
    }

    public Integer getGrams() {
        return grams;
    }

    public Integer getTotalGrams() {
        return totalGrams;
    }

    public Temperature getDegreesC() {
        return degreesC;
    }

    public Properties getConfig() {
        return config;
    }
}
