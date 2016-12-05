package au.com.auspost.smartspb.dao;

import au.com.auspost.smartspb.domain.Event;
import au.com.auspost.smartspb.domain.Reading;
import au.com.auspost.smartspb.domain.StreetPostingBox;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ReadingDaoIT {

    @Autowired
    private ReadingDao readingDao;

    @Autowired
    private EventDao eventDao;

    @Test
    public void testSave() {
        List<Reading> readings = readingDao.list(new DateTime(2016, 11, 1, 0, 0, 0));
        assertThat(readings.size(), is(2));
        assertThat(readings.get(0).getEvent().isLatestForType(), is(true));
        assertThat(readings.get(1).getEvent().isLatestForType(), is(false));

        StreetPostingBox spb = new StreetPostingBox();
        spb.setId(1);

        Event event = new Event(spb, Event.Type.READING, new DateTime(), Temperature.valueOf("27.3"), true);
        Reading reading = new Reading(event, 1012, 1200, 1);
        eventDao.clearLatest(spb.getId(), Event.Type.READING);
        eventDao.save(event);
        readingDao.save(reading);

        readings = readingDao.list(new DateTime(2016, 11, 1, 0, 0, 0));
        assertThat(readings.size(), is(3));

        assertThat(readings.get(0).getId(), is(3));
        assertThat(readings.get(0).getGrams(), is(1012));
        assertThat(readings.get(0).getTotalGrams(), is(1200));
        assertThat(readings.get(0).getArticleCount(), is(1));
    }

    @Test
    public void testList() {
        List<Reading> readings = readingDao.list(new DateTime(2016, 11, 1, 0, 0, 0));

        assertThat(readings.size(), is(2));
        assertThat(readings.get(0).getEvent(), notNullValue());
        assertThat(readings.get(1).getEvent(), notNullValue());
        assertThat(readings.get(0).getEvent().getStreetPostingBox(), sameInstance(readings.get(1).getEvent().getStreetPostingBox()));

        assertThat(readings.get(0).getEvent().getStreetPostingBox().getReadings(), is(readings));

        assertThat(readings.get(0).getEvent().getStreetPostingBox().getImei(), is("359769034498003"));
        assertThat(readings.get(0).getEvent().getStreetPostingBox().getTimezone(), is(TimeZone.getTimeZone("Australia/Perth")));
        assertThat(readings.get(0).getEvent().getStreetPostingBox().getApiKey(), is("16fa2ee7-6614-4f62-bc16-a3c6fa189675"));
        assertThat(readings.get(0).getEvent().getStreetPostingBox().getPrevApiKey(), is("a73c5740-1cde-40a9-bde7-1d5e44761f77"));
        assertThat(readings.get(0).getEvent().getStreetPostingBox().getVersion(), is(1));

        assertThat(readings.get(0).getEvent().getDateTime(), is(new DateTime(2016, 11, 1, 7, 1, 0)));
        assertThat(readings.get(0).getGrams(), is(11));
        assertThat(readings.get(0).getTotalGrams(), is(161));
        assertThat(readings.get(0).getArticleCount(), is(2));
        assertThat(readings.get(0).getEvent().getDegreesC(), is(Temperature.valueOf("21.4")));
        assertThat(readings.get(0).getEvent().isLatestForType(), is(true));

        assertThat(readings.get(1).getEvent().getDateTime(), is(new DateTime(2016, 11, 1, 7, 0, 0)));
        assertThat(readings.get(1).getGrams(), is(10));
        assertThat(readings.get(1).getTotalGrams(), is(150));
        assertThat(readings.get(1).getArticleCount(), is(1));
        assertThat(readings.get(1).getEvent().getDegreesC(), is(Temperature.valueOf("21.3")));
        assertThat(readings.get(1).getEvent().isLatestForType(), is(false));
    }
}
