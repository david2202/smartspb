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
import au.com.auspost.smartspb.web.value.remote.EventVO;
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
public class RemoteEventRestController {
    @Autowired
    private StreetPostingBoxService streetPostingBoxService;

    @Autowired
    private RemoteConfigurationService remoteConfigurationService;

    private SimpMessagingTemplate template;

    @Autowired
    private EventService eventService;

    @Autowired
    public RemoteEventRestController(SimpMessagingTemplate template) {
        this.template = template;
        template.setMessageConverter(new MappingJackson2MessageConverter());
    }

    @RequestMapping(value = "/spb/{imei}/event", method = RequestMethod.POST)
    public ConfigVersionVO create(@RequestBody List<EventVO> eventVOs,
                                  @RequestHeader("apiKey") String apiKey,
                                  @PathVariable("imei") String imei) {
        StreetPostingBox spb = streetPostingBoxService.load(imei);
        if (!spb.checkApiKey(apiKey)) {
            throw new UnauthorisedAccessException();
        }

        List<Event> events = new ArrayList<>();
        for (EventVO eventVO : eventVOs) {
            DateTime readingDateTime = new DateTime().minusSeconds(eventVO.getSecondsAgo());
            Event event = new Event(spb, Event.Type.valueOf(eventVO.getType()), readingDateTime, eventVO.getDegreesC(), false);
            if (Event.Type.valueOf(eventVO.getType()) == Event.Type.READING) {
                Reading reading = new Reading(event, eventVO.getGrams(), eventVO.getTotalGrams(), eventVO.getArticleCount());
                event.makeReading(reading);
            }
            events.add(event);
        }
        eventService.saveEvents(events);

        RemoteConfiguration remoteConfiguration = remoteConfigurationService.load();
        this.template.convertAndSend("/topic/readingsUpdate", eventVOs);
        this.template.convertAndSend("/topic/eventsUpdate", eventVOs);
        return new ConfigVersionVO(spb, remoteConfiguration);
    }
}
