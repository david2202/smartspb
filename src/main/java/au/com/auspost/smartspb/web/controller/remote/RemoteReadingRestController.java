package au.com.auspost.smartspb.web.controller.remote;

import au.com.auspost.smartspb.domain.Reading;
import au.com.auspost.smartspb.domain.RemoteConfiguration;
import au.com.auspost.smartspb.domain.StreetPostingBox;
import au.com.auspost.smartspb.service.ReadingService;
import au.com.auspost.smartspb.service.RemoteConfigurationService;
import au.com.auspost.smartspb.service.StreetPostingBoxService;
import au.com.auspost.smartspb.web.exception.UnauthorisedAccessException;
import au.com.auspost.smartspb.web.value.remote.ConfigVersionVO;
import au.com.auspost.smartspb.web.value.remote.ReadingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rest/remote")
public class RemoteReadingRestController {
    @Autowired
    private StreetPostingBoxService streetPostingBoxService;

    @Autowired
    private RemoteConfigurationService remoteConfigurationService;

    @Autowired
    private ReadingService readingService;

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
        for (ReadingVO readingVO : readingVOs) {
            Reading reading = new Reading(spb, readingVO.getGrams(), readingVO.getDegreesC());
            readingService.save(reading);
        }

        RemoteConfiguration remoteConfiguration = remoteConfigurationService.load();
        this.template.convertAndSend("/topic/readingsUpdate", readingVOs);
        return new ConfigVersionVO(spb, remoteConfiguration);
    }
}
