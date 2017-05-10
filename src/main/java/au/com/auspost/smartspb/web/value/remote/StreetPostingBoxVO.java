package au.com.auspost.smartspb.web.value.remote;

import au.com.auspost.smartspb.domain.RemoteConfiguration;
import au.com.auspost.smartspb.domain.StreetPostingBox;

import java.util.Properties;

public class StreetPostingBoxVO {
    private String imei;
    private Integer version;
    private String apiKey;
    private Properties config;

    public StreetPostingBoxVO(StreetPostingBox streetPostingBox, RemoteConfiguration remoteConfiguration) {
        this.imei = streetPostingBox.getImei();
        this.version = streetPostingBox.getVersion();
        this.apiKey = streetPostingBox.getApiKey();
        this.config = remoteConfiguration.getPropertiesAsProperties();
    }

    public StreetPostingBoxVO(StreetPostingBox streetPostingBox) {
        this.imei = streetPostingBox.getImei();
        this.version = streetPostingBox.getVersion();
        this.apiKey = streetPostingBox.getApiKey();
        this.config = null;
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

    public Properties getConfig() {
        return config;
    }
}
