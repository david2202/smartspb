package au.com.auspost.smartspb.dao;

import au.com.auspost.smartspb.domain.Reading;
import au.com.auspost.smartspb.domain.Temperature;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.TimeZone;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ReadingRepositoryIT {
    @Autowired
    private ReadingCrudRepository readingCrudRepository;

    @Test
    public void testFindByDateTimeGreaterThanOrderByDateTimeDesc() {
        List<Reading> readings = readingCrudRepository.findByDateTimeGreaterThanOrderByDateTimeDesc(new DateTime(2016, 11, 1, 7, 0, 30));

        assertThat(readings.size(), is(1));
        assertThat(readings.get(0).getDateTime(), is(new DateTime(2016, 11, 1, 7, 1, 0)));
        assertThat(readings.get(0).getGrams(), is(11));
        assertThat(readings.get(0).getTotalGrams(), is(161));
        assertThat(readings.get(0).getArticleCount(), is(2));
        assertThat(readings.get(0).getDegreesC(), is(Temperature.valueOf("21.4")));
        assertThat(readings.get(0).isLatest(), is(true));

        assertThat(readings.get(0).getStreetPostingBox().getImei(), is("359769034498003"));
        assertThat(readings.get(0).getStreetPostingBox().getTimezone(), is(TimeZone.getTimeZone("Australia/Hobart")));
        assertThat(readings.get(0).getStreetPostingBox().getApiKey(), is("16fa2ee7-6614-4f62-bc16-a3c6fa189675"));
        assertThat(readings.get(0).getStreetPostingBox().getPrevApiKey(), is("a73c5740-1cde-40a9-bde7-1d5e44761f77"));
        assertThat(readings.get(0).getStreetPostingBox().getVersion(), is(1));
    }
}
