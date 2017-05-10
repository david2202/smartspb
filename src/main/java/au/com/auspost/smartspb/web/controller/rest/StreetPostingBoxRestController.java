package au.com.auspost.smartspb.web.controller.rest;


import au.com.auspost.smartspb.dao.StreetPostingBoxCrudRepository;
import au.com.auspost.smartspb.web.value.StreetPostingBoxVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/rest/api")
public class StreetPostingBoxRestController {
    @Autowired
    private StreetPostingBoxCrudRepository streetPostingBoxCrudRepository;

    @CrossOrigin
    @RequestMapping(value = "/spbs", method = RequestMethod.GET)
    public List<StreetPostingBoxVO> list(@RequestParam(name = "timeZone", required = false) Integer timeZoneOffsetMinutes) {
        final String timeZoneOffset;
        if (timeZoneOffsetMinutes != null) {
            Integer timeZoneHours = timeZoneOffsetMinutes / 60;
            Integer timeZoneMinutes = timeZoneOffsetMinutes % 60;
            timeZoneOffset = String.format("GMT%+02d:%02d", timeZoneHours, timeZoneMinutes);
        } else {
            timeZoneOffset = null;
        }
        List<StreetPostingBoxVO> spbs = new ArrayList<>();
        streetPostingBoxCrudRepository.findAll().forEach((v) -> spbs.add(new StreetPostingBoxVO(v, timeZoneOffset)));
        return spbs;
    }
}
