package au.com.auspost.smartspb.dao;

import au.com.auspost.smartspb.domain.Reading;
import org.joda.time.DateTime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReadingCrudRepository extends CrudRepository<Reading, Integer> {

    @Transactional(readOnly = true)
    List<Reading> findByDateTimeGreaterThanOrderByDateTimeDesc(DateTime start);

}
