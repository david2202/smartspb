package au.com.auspost.smartspb.web.value.remote;

import au.com.auspost.smartspb.domain.Temperature;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

public class ReadingVO {
    private Integer grams;

    private Integer totalGrams;

    private Temperature degreesC;

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

    public Temperature getDegreesC() {
        return degreesC;
    }

    public void setDegreesC(Temperature degreesC) {
        this.degreesC = degreesC;
    }
}
