package au.com.auspost.smartspb.web.controller.rest.remote;

import au.com.auspost.smartspb.domain.RemoteConfiguration;
import au.com.auspost.smartspb.domain.RemoteConfigurationProperty;
import au.com.auspost.smartspb.domain.StreetPostingBox;
import au.com.auspost.smartspb.service.RemoteConfigurationService;
import au.com.auspost.smartspb.service.StreetPostingBoxService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RemoteStreetPostingBoxRestController.class)
public class RemoteStreetPostingBoxRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private StreetPostingBoxService streetPostingBoxService;

    @MockBean
    private RemoteConfigurationService remoteConfigurationService;

    @Test
    public void testGet() throws Exception {
        StreetPostingBox spb = makeStreetPostingBox();
        RemoteConfiguration remoteConfiguration = makeRemoteConfiguration();

        when(streetPostingBoxService.load(spb.getImei())).thenReturn(spb);
        when(remoteConfigurationService.load()).thenReturn(remoteConfiguration);

        mvc.perform(get("/rest/remote/spb/" + spb.getImei())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("apiKey", spb.getApiKey())
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.imei", is(spb.getImei())))
                .andExpect(jsonPath("$.version", is(spb.getVersion())))
                .andExpect(jsonPath("$.apiKey", is(spb.getApiKey())))
                .andExpect(jsonPath("$.config.property", is(remoteConfiguration.getPropertiesAsProperties().get("property"))));
    }

    @Test
    public void testGetUnauthorised() throws Exception {
        StreetPostingBox spb = makeStreetPostingBox();

        when(streetPostingBoxService.load(spb.getImei())).thenReturn(spb);

        mvc.perform(get("/rest/remote/spb/" + spb.getImei())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("apiKey", "somerandomkey")
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isUnauthorized());
    }

    public StreetPostingBox makeStreetPostingBox() {
        StreetPostingBox spb = new StreetPostingBox();
        spb.setImei("IMEI01234567890");
        spb.setApiKey(UUID.randomUUID().toString());
        spb.setVersion(15);
        return spb;
    }

    private RemoteConfiguration makeRemoteConfiguration() {
        RemoteConfiguration rc = new RemoteConfiguration(13);
        rc.addProperty(new RemoteConfigurationProperty("property", "value"));
        return rc;
    }
}
