package au.com.auspost.smartspb.service;

import au.com.auspost.smartspb.dao.EventDao;
import au.com.auspost.smartspb.dao.ReadingDao;
import au.com.auspost.smartspb.domain.Event;
import au.com.auspost.smartspb.domain.Reading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventDao eventDao;
    @Autowired
    private ReadingDao readingDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Reading reading) {
        readingDao.save(reading);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(List<Reading> readings) {
        eventDao.clearLatest(readings.get(0).getEvent().getStreetPostingBox().getId(), Event.Type.READING);
        for (Reading reading : readings) {
            eventDao.save(reading.getEvent());
            readingDao.save(reading);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveEvents(List<Event> events) {
        for (Event event : events) {
            eventDao.clearLatest(event.getStreetPostingBox().getId(), event.getType());
            event.makeLatest();
            eventDao.save(event);
            if (event.getReading() != null) {
                readingDao.save(event.getReading());
            }
        }
    }
}
