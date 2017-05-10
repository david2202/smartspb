package au.com.auspost.smartspb.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ReadingController {

    @RequestMapping(path = "/readings", method = RequestMethod.GET)
    public String get() {
        return "readings";
    }
}
