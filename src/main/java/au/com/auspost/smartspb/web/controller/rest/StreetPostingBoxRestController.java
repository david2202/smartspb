package au.com.auspost.smartspb.web.controller.rest;


import au.com.auspost.smartspb.dao.ReadingDao;
import au.com.auspost.smartspb.dao.StreetPostingBoxDao;
import au.com.auspost.smartspb.domain.Reading;
import au.com.auspost.smartspb.domain.StreetPostingBox;
import au.com.auspost.smartspb.web.value.StreetPostingBoxVO;
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
public class StreetPostingBoxRestController {
    @Autowired
    private StreetPostingBoxDao streetPostingBoxDao;

    @RequestMapping(value = "/spbs", method = RequestMethod.GET)
    public List<StreetPostingBoxVO> list() {
        List<StreetPostingBoxVO> spbs = new ArrayList<>();
        for (StreetPostingBox spb:streetPostingBoxDao.list()) {
            spbs.add(new StreetPostingBoxVO(spb));
        }
        return spbs;
    }
}
