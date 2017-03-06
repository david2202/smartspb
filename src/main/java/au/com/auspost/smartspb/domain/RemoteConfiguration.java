package au.com.auspost.smartspb.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Version;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Entity
public class RemoteConfiguration {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="remote_configuration_id", referencedColumnName = "id")
    @OrderBy("name ASC")
    private List<RemoteConfigurationProperty> properties;

    @Version
    private Integer version;

    public RemoteConfiguration() {
        // Needed for hibernate
    }

    public RemoteConfiguration(Integer version) {
        this.version = version;
    }

    public List<RemoteConfigurationProperty> getProperties() {
        if (properties == null) {
            return Collections.EMPTY_LIST;
        } else {
            return Collections.unmodifiableList(properties);
        }
    }

    public void addProperty(RemoteConfigurationProperty rcp) {
        if (properties == null) {
            properties = new ArrayList<>();
        }
        properties.add(rcp);
    }

    public Properties getPropertiesAsProperties() {
        Properties props = new Properties();
        for (RemoteConfigurationProperty remoteConfigurationProperty : getProperties()) {
            props.put(remoteConfigurationProperty.getName(), remoteConfigurationProperty.getValue());
        }
        return props;
    }

    public Integer getVersion() {
        return version;
    }
}
