package au.com.auspost.smartspb.web.controller.rest.remote;

import au.com.auspost.smartspb.domain.Event;
import au.com.auspost.smartspb.domain.Reading;
import au.com.auspost.smartspb.domain.RemoteConfiguration;
import au.com.auspost.smartspb.domain.StreetPostingBox;
import au.com.auspost.smartspb.service.EventService;
import au.com.auspost.smartspb.service.RemoteConfigurationService;
import au.com.auspost.smartspb.service.StreetPostingBoxService;
import au.com.auspost.smartspb.web.exception.UnauthorisedAccessException;
import au.com.auspost.smartspb.web.value.remote.ConfigVersionVO;
import au.com.auspost.smartspb.web.value.remote.ReadingVO;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/rest/remote")
public class RemoteReadingRestController {
    @Autowired
    private StreetPostingBoxService streetPostingBoxService;

    @Autowired
    private RemoteConfigurationService remoteConfigurationService;

    @Autowired
    private EventService eventService;

    private SimpMessagingTemplate template;

    @Autowired
    public RemoteReadingRestController(SimpMessagingTemplate template) {
        this.template = template;
        template.setMessageConverter(new MappingJackson2MessageConverter());
    }

    @RequestMapping(value = "/spb/{imei}/reading", method = RequestMethod.POST)
    public ConfigVersionVO create(@RequestBody List<ReadingVO> readingVOs,
                                  @RequestHeader("apiKey") String apiKey,
                                  @PathVariable("imei") String imei) {
        StreetPostingBox spb = streetPostingBoxService.load(imei);
        if (!spb.checkApiKey(apiKey)) {
            throw new UnauthorisedAccessException();
        }

        List<Reading> readings = new ArrayList<>();
        for (ReadingVO readingVO : readingVOs) {
            DateTime readingDateTime = new DateTime().minusSeconds(readingVO.getSecondsAgo());
            Event event = new Event(spb, Event.Type.READING, readingDateTime, readingVO.getDegreesC(), false);
            Reading reading = new Reading(event, readingVO.getGrams(), readingVO.getTotalGrams(), readingVO.getArticleCount());
            readings.add(reading);
        }
        readings.get(readings.size() - 1).getEvent().makeLatest();
        eventService.save(readings);

        RemoteConfiguration remoteConfiguration = remoteConfigurationService.load();
        this.template.convertAndSend("/topic/readingsUpdate", readingVOs);
        this.template.convertAndSend("/topic/eventsUpdate", readingVOs);
        return new ConfigVersionVO(spb, remoteConfiguration);
    }
}
