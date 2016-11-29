package au.com.auspost.smartspb.web.value;

import au.com.auspost.smartspb.domain.Reading;
import au.com.auspost.smartspb.domain.Temperature;
import au.com.auspost.smartspb.util.json.DateTimeJsonDeserializer;
import au.com.auspost.smartspb.util.json.DateTimeJsonSerializer;
import au.com.auspost.smartspb.util.json.TemperatureJsonDeserializer;
import au.com.auspost.smartspb.util.json.TemperatureJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.TimeZone;

import static au.com.auspost.smartspb.Constants.DATE_FORMAT;

public class ReadingVO {

    private Integer id;
    private Href streetPostingBox;
    private String address;
    private String dateTime;
    private String localDateTime;
    private String localTimeZone;
    private Integer grams;
    private Integer totalGrams;
    private Integer articleCount;
    private Temperature degreesC;

    public ReadingVO(Reading r, String timeZone) {
        this.id = r.getId();
        this.streetPostingBox = new Href("/rest/api/" + r.getStreetPostingBox().getId());
        this.address = r.getStreetPostingBox().getAddress();
        if (timeZone == null) {
            this.dateTime = r.getDateTime().toString(DATE_FORMAT);
        } else {
            this.dateTime = r.getDateTime().toDateTime(DateTimeZone.forTimeZone(TimeZone.getTimeZone(timeZone))).toString(DATE_FORMAT);
        }
        this.localDateTime = r.getLocalDateTime().toString(DATE_FORMAT);
        this.localTimeZone = r.getStreetPostingBox().getTimezone().getID();
        this.grams = r.getGrams();
        this.totalGrams = r.getTotalGrams();
        this.articleCount = r.getArticleCount();
        this.degreesC = r.getDegreesC();
    }

    public Integer getId() {
        return id;
    }

    public Href getStreetPostingBox() {
        return streetPostingBox;
    }

    public String getAddress() {
        return address;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getLocalDateTime() {
        return localDateTime;
    }

    public String getLocalTimeZone() {
        return localTimeZone;
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
}
