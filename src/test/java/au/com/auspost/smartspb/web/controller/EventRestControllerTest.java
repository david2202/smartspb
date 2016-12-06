package au.com.auspost.smartspb.web.controller;

import au.com.auspost.smartspb.dao.EventDao;
import au.com.auspost.smartspb.dao.ReadingDao;
import au.com.auspost.smartspb.domain.Event;
import au.com.auspost.smartspb.domain.Reading;
import au.com.auspost.smartspb.domain.StreetPostingBox;
import au.com.auspost.smartspb.domain.Temperature;
import au.com.auspost.smartspb.web.controller.rest.EventRestController;
import au.com.auspost.smartspb.web.controller.rest.ReadingRestController;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EventRestController.class)
public class EventRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private EventDao eventDao;

    @Test
    public void testGet() throws Exception {
        when(eventDao.list(new DateTime(2016, 10, 31, 0, 0, 0))).thenReturn(makeEvents());

        mvc.perform(get("/rest/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .param("dateTime", "2016-10-31 00:00:00")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.[0].id", is(2)))
                .andExpect(jsonPath("$.[0].streetPostingBox.href", is("/rest/api/1")))
                .andExpect(jsonPath("$.[0].address", is("8 Enterprise Avenue Two Rocks")))
                .andExpect(jsonPath("$.[0].type", is("READING")))
                .andExpect(jsonPath("$.[0].dateTime", is("2016/11/01 07:00:00")))
                .andExpect(jsonPath("$.[0].localDateTime", is("2016/11/01 04:00:00")))
                .andExpect(jsonPath("$.[0].localTimeZone", is("Australia/Perth")))
                .andExpect(jsonPath("$.[0].grams", is(30)))
                .andExpect(jsonPath("$.[0].totalGrams", is(150)))
                .andExpect(jsonPath("$.[0].articleCount", is(2)))
                .andExpect(jsonPath("$.[0].degreesC", is(22.3)))
                .andExpect(jsonPath("$.[1].id", is(1)))
                .andExpect(jsonPath("$.[1].streetPostingBox.href", is("/rest/api/1")))
                .andExpect(jsonPath("$.[1].address", is("8 Enterprise Avenue Two Rocks")))
                .andExpect(jsonPath("$.[1].dateTime", is("2016/11/01 06:59:00")))
                .andExpect(jsonPath("$.[1].localDateTime", is("2016/11/01 03:59:00")))
                .andExpect(jsonPath("$.[1].localTimeZone", is("Australia/Perth")))
                .andExpect(jsonPath("$.[1].grams", is(10)))
                .andExpect(jsonPath("$.[1].totalGrams", is(120)))
                .andExpect(jsonPath("$.[1].articleCount", is(1)))
                .andExpect(jsonPath("$.[1].degreesC", is(22.1)));
    }

    @Test
    public void testGetWithTimeZone() throws Exception {
        when(eventDao.list(new DateTime(2016, 10, 31, 0, 0, 0))).thenReturn(makeEvents());

        mvc.perform(get("/rest/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .param("dateTime", "2016-10-31 00:00:00")
                .param("timeZone", "600")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.[0].id", is(2)))
                .andExpect(jsonPath("$.[0].streetPostingBox.href", is("/rest/api/1")))
                .andExpect(jsonPath("$.[0].address", is("8 Enterprise Avenue Two Rocks")))
                .andExpect(jsonPath("$.[0].dateTime", is("2016/11/01 06:00:00")))
                .andExpect(jsonPath("$.[0].localDateTime", is("2016/11/01 04:00:00")))
                .andExpect(jsonPath("$.[0].grams", is(30)))
                .andExpect(jsonPath("$.[0].totalGrams", is(150)))
                .andExpect(jsonPath("$.[0].articleCount", is(2)))
                .andExpect(jsonPath("$.[0].degreesC", is(22.3)))
                .andExpect(jsonPath("$.[1].id", is(1)))
                .andExpect(jsonPath("$.[1].streetPostingBox.href", is("/rest/api/1")))
                .andExpect(jsonPath("$.[1].address", is("8 Enterprise Avenue Two Rocks")))
                .andExpect(jsonPath("$.[1].dateTime", is("2016/11/01 05:59:00")))
                .andExpect(jsonPath("$.[1].localDateTime", is("2016/11/01 03:59:00")))
                .andExpect(jsonPath("$.[1].localTimeZone", is("Australia/Perth")))
                .andExpect(jsonPath("$.[1].grams", is(10)))
                .andExpect(jsonPath("$.[1].totalGrams", is(120)))
                .andExpect(jsonPath("$.[1].articleCount", is(1)))
                .andExpect(jsonPath("$.[1].degreesC", is(22.1)));
    }

    private List<Event> makeEvents() {
        List<Event> events = new ArrayList<>();

        StreetPostingBox spb = new StreetPostingBox();
        spb.setId(1);
        spb.setTimezone(TimeZone.getTimeZone("Australia/Perth"));
        spb.setAddress("8 Enterprise Avenue Two Rocks");

        Event e2 = new Event(2, spb, Event.Type.READING, new DateTime(2016, 11, 1, 7, 0, 0), Temperature.valueOf("22.3"), true);
        Reading r2 = new Reading(2, e2, 30, 150, 2);
        e2.makeReading(r2);
        events.add(e2);
        Event e1 = new Event(1, spb, Event.Type.READING, new DateTime(2016, 11, 1, 6, 59, 0), Temperature.valueOf("22.1"), false);
        Reading r1 = new Reading(1, e1, 10, 120, 1);
        e1.makeReading(r1);
        events.add(e1);

        return events;
    }

    private String dateString(DateTime dateTime) {
        return dateTime.toString(ISODateTimeFormat.dateTime())  + "[" + DateTimeZone.getDefault() + "]";
    }

    private String localDateString(DateTime dateTime, DateTimeZone timezone) {
        return dateTime.withZone(timezone).toString(ISODateTimeFormat.dateTime())  + "[" + timezone + "]";
    }
}
