package au.com.auspost.smartspb.domain;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reading")
public class Reading {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "street_posting_box_id")
    private StreetPostingBox streetPostingBox;

    private DateTime dateTime;

    private Integer grams;
    private Integer totalGrams;
    private Integer articleCount;
    @Column(name = "degrees_c")
    private Temperature degreesC;

    @Column(name = "latest_ind")
    private Boolean latest;

    public Reading() {
        // Needed for Hibernate
    }

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

    public void setLatest(boolean latest) {
        this.latest = latest;
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
