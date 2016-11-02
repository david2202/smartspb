package au.com.auspost.smartspb.dao;

import au.com.auspost.smartspb.domain.Reading;
import au.com.auspost.smartspb.domain.StreetPostingBox;
import au.com.auspost.smartspb.domain.Temperature;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.TimeZone;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReadingDaoIT {

    @Autowired
    private ReadingDao readingDao;

    @Test
    public void testSave() {
        List<Reading> readings = readingDao.list(new DateTime(2016, 11, 1, 0, 0, 0));
        assertThat(readings.size(), is(2));
        assertThat(readings.get(0).isLatest(), is(true));
        assertThat(readings.get(1).isLatest(), is(false));

        StreetPostingBox spb = new StreetPostingBox();
        spb.setId(1);
        Reading reading = new Reading(spb, 1012, Temperature.valueOf("27.3"));
        readingDao.save(reading);

        assertThat(reading.isLatest(), is(true));

        readings = readingDao.list(new DateTime(2016, 11, 1, 0, 0, 0));
        assertThat(readings.size(), is(3));

        assertThat(readings.get(0).getId(), is(3));
        assertThat(readings.get(0).getGrams(), is(1012));
        assertThat(readings.get(0).getDegreesC(), is(Temperature.valueOf("27.3")));
        assertThat(readings.get(0).isLatest(), is(true));

        assertThat(readings.get(1).isLatest(), is(false));
        assertThat(readings.get(2).isLatest(), is(false));
    }

    @Test
    public void testList() {
        List<Reading> readings = readingDao.list(new DateTime(2016, 11, 1, 0, 0, 0));

        assertThat(readings.size(), is(2));
        assertThat(readings.get(0).getStreetPostingBox(), notNullValue());
        assertThat(readings.get(1).getStreetPostingBox(), notNullValue());
        assertThat(readings.get(0).getStreetPostingBox(), sameInstance(readings.get(1).getStreetPostingBox()));

        assertThat(readings.get(0).getStreetPostingBox().getReadings(), is(readings));

        assertThat(readings.get(0).getStreetPostingBox().getImei(), is("IMEI12345678902"));
        assertThat(readings.get(0).getStreetPostingBox().getTimezone(), is(TimeZone.getTimeZone("Australia/Perth")));
        assertThat(readings.get(0).getStreetPostingBox().getApiKey(), is("16fa2ee7-6614-4f62-bc16-a3c6fa189675"));
        assertThat(readings.get(0).getStreetPostingBox().getPrevApiKey(), is("a73c5740-1cde-40a9-bde7-1d5e44761f77"));
        assertThat(readings.get(0).getStreetPostingBox().getVersion(), is(1));

        assertThat(readings.get(0).getDateTime(), is(new DateTime(2016, 11, 1, 7, 1, 0)));
        assertThat(readings.get(0).getGrams(), is(161));
        assertThat(readings.get(0).getDegreesC(), is(Temperature.valueOf("21.4")));
        assertThat(readings.get(0).isLatest(), is(true));

        assertThat(readings.get(1).getDateTime(), is(new DateTime(2016, 11, 1, 7, 0, 0)));
        assertThat(readings.get(1).getGrams(), is(150));
        assertThat(readings.get(1).getDegreesC(), is(Temperature.valueOf("21.3")));
        assertThat(readings.get(1).isLatest(), is(false));
    }
}
