package au.com.auspost.smartspb.web.value.remote;

import au.com.auspost.smartspb.domain.Temperature;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

public class ReadingVO {
    private Integer grams;
    private Integer totalGrams;
    private Integer articleCount;
    private Temperature degreesC;
    private Integer secondsAgo = 0;

    public Integer getGrams() {
        return grams;
    }

    public void setGrams(Integer grams) {
        this.grams = grams;
    }

    public Integer getTotalGrams() {
        return totalGrams;
    }

    public void setTotalGrams(Integer totalGrams) {
        this.totalGrams = totalGrams;
    }

    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }

    public Temperature getDegreesC() {
        return degreesC;
    }

    public void setDegreesC(Temperature degreesC) {
        this.degreesC = degreesC;
    }

    public Integer getSecondsAgo() {
        return secondsAgo;
    }

    public void setSecondsAgo(Integer secondsAgo) {
        this.secondsAgo = secondsAgo;
    }
}
