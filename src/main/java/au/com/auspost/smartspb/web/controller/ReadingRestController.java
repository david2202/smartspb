package au.com.auspost.smartspb.web.controller;

import au.com.auspost.smartspb.dao.ReadingDao;
import au.com.auspost.smartspb.domain.Reading;
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
public class ReadingRestController {

    @Autowired
    private ReadingDao readingDao;

    @RequestMapping(value = "/readings", method = RequestMethod.GET)
    public List<ReadingVO> list(
            @RequestParam(name ="dateTime") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date dateTime) {
        List<ReadingVO> readings = new ArrayList<>();
        for (Reading r:readingDao.list(new DateTime(dateTime))) {
            readings.add(new ReadingVO(r));
        }
        return readings;
    }
}
