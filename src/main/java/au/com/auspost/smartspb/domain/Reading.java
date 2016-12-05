package au.com.auspost.smartspb.domain;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class Reading {
    private Integer id;
    private Event event;
    private Integer grams;
    private Integer totalGrams;
    private Integer articleCount;

    public Reading(Integer id, Event event, Integer grams, Integer totalGrams, Integer articleCount) {
        this.id = id;
        this.event = event;
        this.grams = grams;
        this.totalGrams = totalGrams;
        this.articleCount = articleCount;
    }

    public Reading(Event event, Integer grams, Integer totalGrams, Integer articleCount) {
        this.event = event;
        this.grams = grams;
        this.totalGrams = totalGrams;
        this.articleCount = articleCount;
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

    public void setEvent(Event event) {
        this.event = event;
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
}
