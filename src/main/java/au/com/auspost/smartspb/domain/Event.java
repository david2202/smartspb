package au.com.auspost.smartspb.domain;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class Event {
    private Integer id;
    private Type type;
    private StreetPostingBox streetPostingBox;
    private DateTime dateTime;
    private Boolean latestForType;

    public Event(Integer id, Type type, StreetPostingBox streetPostingBox, DateTime dateTime, Boolean latestForType) {
        this.id = id;
        this.type = type;
        this.streetPostingBox = streetPostingBox;
        this.dateTime = dateTime;
        this.latestForType = latestForType;
    }

    public Event makeLatest() {
        this.latestForType = true;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public StreetPostingBox getStreetPostingBox() {
        return streetPostingBox;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public DateTime getLocalDateTime() {
        return dateTime.withZone(DateTimeZone.forID(streetPostingBox.getTimezone().getID()));
    }


    public Boolean getLatestForType() {
        return latestForType;
    }
}

enum Type {
    STARTUP, READING;
}