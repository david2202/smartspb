package au.com.auspost.smartspb.domain;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class Reading {
    private Integer id;
    private StreetPostingBox streetPostingBox;
    private DateTime dateTime;
    private Integer grams;
    private Integer totalGrams;
    private Integer articleCount;
    private Temperature degreesC;
    private Boolean latest;

    public Reading(Integer id, StreetPostingBox streetPostingBox, DateTime dateTime, Integer grams, Integer totalGrams, Integer articleCount, Temperature degreesC) {
        this.id = id;
        this.streetPostingBox = streetPostingBox;
        this.dateTime = dateTime;
        this.grams = grams;
        this.totalGrams = totalGrams;
        this.articleCount = articleCount;
        this.degreesC = degreesC;
        this.latest = false;
    }

    public Reading(Integer id, StreetPostingBox streetPostingBox, DateTime dateTime, Integer grams, Integer totalGrams, Integer articleCount, Temperature degreesC, Boolean latest) {
        this.id = id;
        this.streetPostingBox = streetPostingBox;
        this.dateTime = dateTime;
        this.grams = grams;
        this.totalGrams = totalGrams;
        this.articleCount = articleCount;
        this.degreesC = degreesC;
        this.latest = latest;
    }

    public Reading(StreetPostingBox streetPostingBox, DateTime dateTime, Integer grams, Integer totalGrams, Integer articleCount, Temperature degreesC) {
        this.streetPostingBox = streetPostingBox;
        this.dateTime = dateTime;
        this.grams = grams;
        this.totalGrams = totalGrams;
        this.articleCount = articleCount;
        this.degreesC = degreesC;
        this.latest = false;
    }

    public Reading(StreetPostingBox streetPostingBox, DateTime dateTime, Integer grams, Integer totalGrams, Integer articleCount, Temperature degreesC, Boolean latest) {
        this.streetPostingBox = streetPostingBox;
        this.dateTime = dateTime;
        this.grams = grams;
        this.totalGrams = totalGrams;
        this.articleCount = articleCount;
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

    public Integer getTotalGrams() {
        return totalGrams;
    }

    public Integer getArticleCount() {
        return articleCount;
    }

    public Temperature getDegreesC() {
        return degreesC;
    }

    public Boolean isLatest() {
        return latest;
    }
}
