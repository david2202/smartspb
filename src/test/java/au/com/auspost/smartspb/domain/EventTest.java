package au.com.auspost.smartspb.domain;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class EventTest {

    @Test
    public void testLocalTime() {
        StreetPostingBox spb = new StreetPostingBox();
        spb.setTimezone("Australia/Perth");

        DateTime dt = new DateTime(2016, 11, 1, 6, 59, 0, DateTimeZone.forID("Australia/Melbourne"));
        Event e = new Event(1, spb, Event.Type.READING, dt, Temperature.valueOf("22.1"), true);

        assertThat(e.getDateTime().toString(), is("2016-11-01T06:59:00.000+11:00"));
        assertThat(e.getLocalDateTime().toString(), is("2016-11-01T03:59:00.000+08:00"));
    }
}
