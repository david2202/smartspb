package au.com.auspost.smartspb.web.controller.rest.remote;

import au.com.auspost.smartspb.domain.RemoteConfiguration;
import au.com.auspost.smartspb.domain.StreetPostingBox;
import au.com.auspost.smartspb.service.RemoteConfigurationService;
import au.com.auspost.smartspb.service.StreetPostingBoxService;
import au.com.auspost.smartspb.web.exception.UnauthorisedAccessException;
import au.com.auspost.smartspb.web.value.remote.StreetPostingBoxVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/rest/remote")
public class RemoteStreetPostingBoxRestController {
    @Autowired
    private StreetPostingBoxService streetPostingBoxService;

    @Autowired
    private RemoteConfigurationService remoteConfigurationService;

    @RequestMapping(value = "/spb/{imei}", method = RequestMethod.GET)
    public StreetPostingBoxVO create(@PathVariable("imei") String imei,
                                     @RequestHeader("apiKey") String apiKey) {
        StreetPostingBox spb = streetPostingBoxService.load(imei);
        if (!spb.checkApiKey(apiKey)) {
            throw new UnauthorisedAccessException();
        }
        RemoteConfiguration remoteConfiguration = remoteConfigurationService.load();

        return new StreetPostingBoxVO(spb, remoteConfiguration);
    }
}
