package au.com.auspost.smartspb.web.controller.rest;

import au.com.auspost.smartspb.dao.EventDao;
import au.com.auspost.smartspb.dao.ReadingDao;
import au.com.auspost.smartspb.domain.Event;
import au.com.auspost.smartspb.domain.Reading;
import au.com.auspost.smartspb.web.value.EventVO;
import au.com.auspost.smartspb.web.value.ReadingVO;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/rest/api")
public class EventRestController {

    @Autowired
    private EventDao eventDao;

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public List<EventVO> list(
            @RequestParam(name ="dateTime") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateTime,
            @RequestParam(name = "timeZone", required = false) Integer timeZoneOffsetMinutes) {
        String timeZoneOffset = null;
        if (timeZoneOffsetMinutes != null) {
            Integer timeZoneHours = timeZoneOffsetMinutes / 60;
            Integer timeZoneMinutes = timeZoneOffsetMinutes % 60;
            timeZoneOffset = String.format("GMT%+02d:%02d", timeZoneHours, timeZoneMinutes);
        }
        List<EventVO> events = new ArrayList<>();
        for (Event e:eventDao.list(new DateTime(dateTime))) {
            events.add(new EventVO(e, timeZoneOffset));
        }
        return events;
    }
}
