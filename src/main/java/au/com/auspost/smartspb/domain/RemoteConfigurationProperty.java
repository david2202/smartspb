package au.com.auspost.smartspb.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RemoteConfigurationProperty {
    @Id
    private Integer id;

    private String name;
    private String value;
    private String description;

    public RemoteConfigurationProperty() {
        // Needed for hibernate
    }

    public RemoteConfigurationProperty(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
