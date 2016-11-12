package au.com.auspost.smartspb.web.controller;

import au.com.auspost.smartspb.dao.ReadingDao;
import au.com.auspost.smartspb.domain.Reading;
import au.com.auspost.smartspb.domain.StreetPostingBox;
import au.com.auspost.smartspb.domain.Temperature;
import au.com.auspost.smartspb.web.controller.ReadingRestController;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReadingRestController.class)
public class ReadingRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReadingDao readingDao;

    @Test
    public void testGet() throws Exception {
        when(readingDao.list(new DateTime(2016, 10, 31, 0, 0, 0))).thenReturn(makeReadings());

        mvc.perform(get("/rest/api/readings?dateTime=2016-10-31 00:00:00")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.[0].id", is(2)))
                .andExpect(jsonPath("$.[0].streetPostingBox.href", is("/rest/api/1")))
                .andExpect(jsonPath("$.[0].dateTime", is("2016-11-01T07:00:00.000+11:00[Australia/Melbourne]")))
                .andExpect(jsonPath("$.[0].localDateTime", is("2016-11-01T04:00:00.000+08:00[Australia/Perth]")))
                .andExpect(jsonPath("$.[0].grams", is(150)))
                .andExpect(jsonPath("$.[0].degreesC", is(22.3)))
                .andExpect(jsonPath("$.[1].id", is(1)))
                .andExpect(jsonPath("$.[1].streetPostingBox.href", is("/rest/api/1")))
                .andExpect(jsonPath("$.[1].dateTime", is(dateString(new DateTime(2016, 11, 1, 6, 59, 0)))))
                .andExpect(jsonPath("$.[1].localDateTime", is(localDateString(new DateTime(2016, 11, 1, 6, 59, 0), DateTimeZone.forID("Australia/Perth")))))
                .andExpect(jsonPath("$.[1].grams", is(120)))
                .andExpect(jsonPath("$.[1].degreesC", is(22.1)));
    }

    private List<Reading> makeReadings() {
        List<Reading> readings = new ArrayList<>();

        StreetPostingBox spb = new StreetPostingBox();
        spb.setId(1);
        spb.setTimezone(TimeZone.getTimeZone("Australia/Perth"));

        readings.add(new Reading(2, spb, new DateTime(2016, 11, 1, 7, 0, 0), 150, Temperature.valueOf("22.3")));
        readings.add(new Reading(1, spb, new DateTime(2016, 11, 1, 6, 59, 0), 120, Temperature.valueOf("22.1")));

        return readings;
    }

    private String dateString(DateTime dateTime) {
        return dateTime.toString(ISODateTimeFormat.dateTime())  + "[" + DateTimeZone.getDefault() + "]";
    }

    private String localDateString(DateTime dateTime, DateTimeZone timezone) {
        return dateTime.withZone(timezone).toString(ISODateTimeFormat.dateTime())  + "[" + timezone + "]";
    }
}
