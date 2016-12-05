package au.com.auspost.smartspb.web.value;

import au.com.auspost.smartspb.domain.Event;
import au.com.auspost.smartspb.domain.Temperature;
import org.joda.time.DateTimeZone;

import java.util.TimeZone;

import static au.com.auspost.smartspb.Constants.DATE_FORMAT;

public class EventVO {
    private Integer id;
    private Href streetPostingBox;
    private Event.Type type;
    private String address;
    private String dateTime;
    private String localDateTime;
    private String localTimeZone;
    private Integer grams;
    private Integer totalGrams;
    private Integer articleCount;
    private Temperature degreesC;

    public EventVO(Event e, String timeZone) {
        this.id = e.getId();
        this.streetPostingBox = new Href("/rest/api/" + e.getStreetPostingBox().getId());
        this.type = e.getType();
        this.address = e.getStreetPostingBox().getAddress();
        if (timeZone == null) {
            this.dateTime = e.getDateTime().toString(DATE_FORMAT);
        } else {
            this.dateTime = e.getDateTime().toDateTime(DateTimeZone.forTimeZone(TimeZone.getTimeZone(timeZone))).toString(DATE_FORMAT);
        }
        this.localDateTime = e.getLocalDateTime().toString(DATE_FORMAT);
        this.localTimeZone = e.getStreetPostingBox().getTimezone().getID();
        this.grams = e.getReading().getGrams();
        this.totalGrams = e.getReading().getTotalGrams();
        this.articleCount = e.getReading().getArticleCount();
        this.degreesC = e.getDegreesC();
    }

    public Integer getId() {
        return id;
    }

    public Href getStreetPostingBox() {
        return streetPostingBox;
    }

    public Event.Type getType() {
        return type;
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
