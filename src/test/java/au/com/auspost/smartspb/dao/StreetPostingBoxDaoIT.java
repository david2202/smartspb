package au.com.auspost.smartspb.dao;

import au.com.auspost.smartspb.domain.StreetPostingBox;
import au.com.auspost.smartspb.domain.Temperature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.TimeZone;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StreetPostingBoxDaoIT {
    @Autowired
    private StreetPostingBoxDao streetPostingBoxDao;

    @Test
    public void testLoadByImei() {
        StreetPostingBox spb = streetPostingBoxDao.load("359769034498003");

        assertSpb1(spb);
    }

    private void assertSpb1(StreetPostingBox spb) {
        assertThat(spb.getImei(), is("359769034498003"));
        assertThat(spb.getId(), is(1));
        assertThat(spb.getTimezone(), is(TimeZone.getTimeZone("Australia/Perth")));
        assertThat(spb.getAddress(), is("82 St Andrews Drive Yanchep"));
        assertThat(spb.getPostCode(), is(6035));
        assertThat(spb.getLatLong(), is(notNullValue()));
        assertThat(spb.getLatLong().getLatitude(), is(BigDecimal.valueOf(-31540410, 6)));
        assertThat(spb.getLatLong().getLongitude(), is(BigDecimal.valueOf(115651000, 6)));
        assertThat(spb.getLatestReading(), is(notNullValue()));
        assertThat(spb.getLatestReading().isLatest(), is(true));
        assertThat(spb.getLatestReading().getGrams(), is(11));
        assertThat(spb.getLatestReading().getTotalGrams(), is(161));
        assertThat(spb.getLatestReading().getArticleCount(), is(2));
        assertThat(spb.getLatestReading().getDegreesC(), is(new Temperature(BigDecimal.valueOf(214, 1))));
        assertThat(spb.getApiKey(), is("16fa2ee7-6614-4f62-bc16-a3c6fa189675"));
        assertThat(spb.getPrevApiKey(), is("a73c5740-1cde-40a9-bde7-1d5e44761f77"));
        assertThat(spb.getVersion(), is(1));
    }

    @Test
    public void testList() {
        List<StreetPostingBox> spbs = streetPostingBoxDao.list();

        assertThat(spbs.size(), is(3));

        assertSpb1(spbs.get(0));
    }


}
