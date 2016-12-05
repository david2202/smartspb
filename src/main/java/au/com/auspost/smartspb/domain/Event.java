package au.com.auspost.smartspb.domain;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class Event {
    public enum Type {
        STARTUP, READING, CHECKIN, CLEARANCE;
    }

    private Integer id;
    private Reading reading;
    private Type type;
    private StreetPostingBox streetPostingBox;
    private DateTime dateTime;
    private Temperature degreesC;
    private Boolean latestForType;

    public Event(Integer id, StreetPostingBox streetPostingBox, Type type, DateTime dateTime, Temperature degreesC, Boolean latestForType) {
        this.id = id;
        this.type = type;
        this.streetPostingBox = streetPostingBox;
        this.dateTime = dateTime;
        this.degreesC = degreesC;
        this.latestForType = latestForType;
    }

    public Event(StreetPostingBox streetPostingBox, Type type, DateTime dateTime, Temperature degreesC, Boolean latestForType) {
        this.type = type;
        this.streetPostingBox = streetPostingBox;
        this.dateTime = dateTime;
        this.degreesC = degreesC;
        this.latestForType = latestForType;
    }

    public Event makeReading(Reading reading) {
        this.reading = reading;
        reading.setEvent(this);
        return this;
    }

    public Event makeLatest() {
        this.latestForType = true;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public StreetPostingBox getStreetPostingBox() {
        return streetPostingBox;
    }

    public Reading getReading() {
        return reading;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public DateTime getLocalDateTime() {
        return dateTime.withZone(DateTimeZone.forID(streetPostingBox.getTimezone().getID()));
    }

    public Temperature getDegreesC() {
        return degreesC;
    }

    public Boolean isLatestForType() {
        return latestForType;
    }
}
