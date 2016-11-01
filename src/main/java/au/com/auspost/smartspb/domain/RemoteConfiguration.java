package au.com.auspost.smartspb.domain;

import java.rmi.Remote;
import java.util.Properties;

public class RemoteConfiguration {
    private Properties properties = new Properties();
    private Integer version;

    public RemoteConfiguration(Integer version) {
        this.version = version;
    }

    public Properties getProperties() {
        return properties;
    }

    public Integer getVersion() {
        return version;
    }
}
