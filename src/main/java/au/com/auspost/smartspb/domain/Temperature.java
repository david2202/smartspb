package au.com.auspost.smartspb.domain;

import au.com.auspost.smartspb.dao.TemperatureUserType;
import au.com.auspost.smartspb.util.json.TemperatureJsonDeserializer;
import au.com.auspost.smartspb.util.json.TemperatureJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.TypeDef;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.math.BigDecimal;

@MappedSuperclass // Workaground to get Hibernate to pick it up
@TypeDef(defaultForType = Temperature.class, typeClass = TemperatureUserType.class)
@JsonSerialize(using = TemperatureJsonSerializer.class)
@JsonDeserialize(using = TemperatureJsonDeserializer.class)
public class Temperature implements Serializable {
    private BigDecimal value;

    public static Temperature valueOf(String temperature) {
        BigDecimal t = new BigDecimal(temperature);
        return new Temperature(t.setScale(1, BigDecimal.ROUND_HALF_UP));
    }

    public Temperature(BigDecimal temperature) {
        if (temperature == null) {
            this.value = null;
        } else {
            this.value = temperature.setScale(1, BigDecimal.ROUND_HALF_UP);
        }
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Temperature)) {
            return false;
        }
        return value.equals(((Temperature) obj).getValue());
    }
}
