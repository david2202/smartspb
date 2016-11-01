package au.com.auspost.smartspb.domain;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class Reading {
    private Integer id;
    private StreetPostingBox spb;
    private DateTime dateTime;
    private Integer grams;
    private Temperature degreesC;

    public Reading(Integer id, StreetPostingBox spb, DateTime dateTime, Integer grams, Temperature degreesC) {
        this.id = id;
        this.spb = spb;
        this.dateTime = dateTime;
        this.grams = grams;
        this.degreesC = degreesC;
    }

    public Reading(StreetPostingBox spb, Integer grams, Temperature degreesC) {
        this.spb = spb;
        this.dateTime = new DateTime();
        this.grams = grams;
        this.degreesC = degreesC;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StreetPostingBox getStreetPostingBox() {
        return spb;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public DateTime getLocalDateTime() {
        return dateTime.withZone(DateTimeZone.forID(spb.getTimezone().getID()));
    }

    public Integer getGrams() {
        return grams;
    }

    public Temperature getDegreesC() {
        return degreesC;
    }
}
