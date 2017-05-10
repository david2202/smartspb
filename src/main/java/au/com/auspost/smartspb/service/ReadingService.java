package au.com.auspost.smartspb.service;

import au.com.auspost.smartspb.dao.ReadingCrudRepository;
import au.com.auspost.smartspb.domain.Reading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReadingService {
    @Autowired
    private ReadingCrudRepository readingCrudRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Reading reading) {
        readingCrudRepository.save(reading);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(List<Reading> readings) {
        readingCrudRepository.save(readings);
    }
}
