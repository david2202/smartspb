package au.com.auspost.smartspb.domain;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class Reading {
    private Integer id;
    private StreetPostingBox streetPostingBox;
    private DateTime dateTime;
    private Integer grams;
    private Temperature degreesC;
    private Boolean latest;

    public Reading(Integer id, StreetPostingBox streetPostingBox, DateTime dateTime, Integer grams, Temperature degreesC) {
        this.id = id;
        this.streetPostingBox = streetPostingBox;
        this.dateTime = dateTime;
        this.grams = grams;
        this.degreesC = degreesC;
        this.latest = false;
    }

    public Reading(Integer id, StreetPostingBox streetPostingBox, DateTime dateTime, Integer grams, Temperature degreesC, Boolean latest) {
        this.id = id;
        this.streetPostingBox = streetPostingBox;
        this.dateTime = dateTime;
        this.grams = grams;
        this.degreesC = degreesC;
        this.latest = latest;
    }

    public Reading(StreetPostingBox streetPostingBox, Integer grams, Temperature degreesC) {
        this.streetPostingBox = streetPostingBox;
        this.dateTime = new DateTime();
        this.grams = grams;
        this.degreesC = degreesC;
        this.latest = false;
    }

    public Reading(StreetPostingBox streetPostingBox, Integer grams, Temperature degreesC, Boolean latest) {
        this.streetPostingBox = streetPostingBox;
        this.dateTime = new DateTime();
        this.grams = grams;
        this.degreesC = degreesC;
        this.latest = latest;
    }

    public Reading makeLatest() {
        this.latest = true;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getGrams() {
        return grams;
    }

    public Temperature getDegreesC() {
        return degreesC;
    }

    public Boolean isLatest() {
        return latest;
    }
}
