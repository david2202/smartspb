package au.com.auspost.smartspb.dao;

import au.com.auspost.smartspb.domain.Reading;
import au.com.auspost.smartspb.domain.StreetPostingBox;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.TimeZone;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class StreetPostingBoxRepositoryIT {
    @Autowired
    private StreetPostingBoxCrudRepository streetPostingBoxCrudRepository;

    @Test
    public void testLoadByImei() {
        StreetPostingBox spb = streetPostingBoxCrudRepository.findOneByImei("359769034498003");
        assertThat(spb.getImei(), is("359769034498003"));
        assertThat(spb.getId(), is(1));
        assertThat(spb.getTimezone(), is(TimeZone.getTimeZone("Australia/Hobart")));
        assertThat(spb.getAddress(), is("152 Bligh St, Warrane"));
        assertThat(spb.getPostcode(), is(7018));
        assertThat(spb.getLatLong(), is(notNullValue()));
        assertThat(spb.getLatLong().getLatitude(), is(BigDecimal.valueOf(-42856737, 6)));
        assertThat(spb.getLatLong().getLongitude(), is(BigDecimal.valueOf(147381942, 6)));
        assertThat(spb.getApiKey(), is("16fa2ee7-6614-4f62-bc16-a3c6fa189675"));
        assertThat(spb.getPrevApiKey(), is("a73c5740-1cde-40a9-bde7-1d5e44761f77"));
        assertThat(spb.getVersion(), is(1));

        Reading latest = spb.getLatestReading();
        assertThat(latest.isLatest(), is(true));
        assertThat(latest.getDateTime(), is(new DateTime(2016, 11, 1, 7, 1, 0)));
    }

    @Test
    public void testClearLatest() {
        StreetPostingBox spb = streetPostingBoxCrudRepository.findOneByImei("359769034498003");
        spb.clearLatest();
    }
}
