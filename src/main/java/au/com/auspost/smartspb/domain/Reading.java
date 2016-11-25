package au.com.auspost.smartspb.domain;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class Reading {
    private Integer id;
    private Event event;
    private Integer grams;
    private Integer totalGrams;
    private Integer articleCount;
    private Temperature degreesC;

    public Reading(Integer id, Event event, Integer grams, Integer totalGrams, Integer articleCount, Temperature degreesC) {
        this.id = id;
        this.event = event;
        this.grams = grams;
        this.totalGrams = totalGrams;
        this.articleCount = articleCount;
        this.degreesC = degreesC;
    }

    public Reading(Event event, Integer grams, Integer totalGrams, Integer articleCount, Temperature degreesC) {
        this.event = event;
        this.grams = grams;
        this.totalGrams = totalGrams;
        this.articleCount = articleCount;
        this.degreesC = degreesC;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
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
