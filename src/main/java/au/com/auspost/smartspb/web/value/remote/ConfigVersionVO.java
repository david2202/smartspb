package au.com.auspost.smartspb.web.value.remote;


import au.com.auspost.smartspb.domain.RemoteConfiguration;
import au.com.auspost.smartspb.domain.StreetPostingBox;

public class ConfigVersionVO {
    private Integer configVersion;
    private Integer spbVersion;

    public ConfigVersionVO(StreetPostingBox streetPostingBox, RemoteConfiguration remoteConfiguration) {
        this.spbVersion = streetPostingBox.getVersion();
        this.configVersion = remoteConfiguration.getVersion();
    }

    public Integer getConfigVersion() {
        return configVersion;
    }

    public Integer getSpbVersion() {
        return spbVersion;
    }
}
