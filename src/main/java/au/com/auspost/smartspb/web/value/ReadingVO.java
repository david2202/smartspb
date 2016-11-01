package au.com.auspost.smartspb.web.value;

import au.com.auspost.smartspb.domain.Reading;
import au.com.auspost.smartspb.domain.Temperature;
import au.com.auspost.smartspb.util.json.DateTimeJsonDeserializer;
import au.com.auspost.smartspb.util.json.DateTimeJsonSerializer;
import au.com.auspost.smartspb.util.json.TemperatureJsonDeserializer;
import au.com.auspost.smartspb.util.json.TemperatureJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;

public class ReadingVO {
    private Integer id;
    private Href streetPostingBox;
    private DateTime dateTime;
    private DateTime localDateTime;
    private Integer grams;
    private Temperature degreesC;

    public ReadingVO(Reading r) {
        this.id = r.getId();
        this.streetPostingBox = new Href("/rest/api/" + r.getStreetPostingBox().getId());
        this.dateTime = r.getDateTime();
        this.localDateTime = r.getLocalDateTime();
        this.grams = r.getGrams();
        this.degreesC = r.getDegreesC();
    }

    public Integer getId() {
        return id;
    }

    public Href getStreetPostingBox() {
        return streetPostingBox;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public DateTime getLocalDateTime() {
        return localDateTime;
    }

    public Integer getGrams() {
        return grams;
    }

    public Temperature getDegreesC() {
        return degreesC;
    }
}
