package au.com.auspost.smartspb.web.controller.remote;

import au.com.auspost.smartspb.domain.RemoteConfiguration;
import au.com.auspost.smartspb.domain.StreetPostingBox;
import au.com.auspost.smartspb.domain.Temperature;
import au.com.auspost.smartspb.service.ReadingService;
import au.com.auspost.smartspb.service.RemoteConfigurationService;
import au.com.auspost.smartspb.service.StreetPostingBoxService;
import au.com.auspost.smartspb.web.value.remote.ReadingVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RemoteReadingRestController.class)
public class RemoteReadingRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReadingService readingService;

    @MockBean
    private StreetPostingBoxService streetPostingBoxService;

    @MockBean
    private RemoteConfigurationService remoteConfigurationService;

    @Test
    public void testPost() throws Exception {
        StreetPostingBox spb = makeStreetPostingBox();
        RemoteConfiguration remoteConfiguration = makeRemoteConfiguration();

        ReadingVO reading = new ReadingVO();
        reading.setGrams(15);
        reading.setDegreesC(Temperature.valueOf("21.5"));

        when(streetPostingBoxService.load(spb.getImei())).thenReturn(spb);
        when(remoteConfigurationService.load()).thenReturn(remoteConfiguration);

        mvc.perform(post("/rest/remote/spb/" + spb.getImei() + "/reading")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("apiKey", spb.getApiKey())
                .content(new ObjectMapper().writeValueAsBytes(reading))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.configVersion", is(remoteConfiguration.getVersion())))
                .andExpect(jsonPath("$.spbVersion", is(spb.getVersion())));
    }

    @Test
    public void testPostUnauthorised() throws Exception {
        StreetPostingBox spb = makeStreetPostingBox();

        ReadingVO reading = new ReadingVO();
        reading.setGrams(15);
        reading.setDegreesC(Temperature.valueOf("21.5"));

        when(streetPostingBoxService.load(spb.getImei())).thenReturn(spb);

        mvc.perform(post("/rest/remote/spb/" + spb.getImei() + "/reading")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("apiKey", "somerandomekey")
                .content(new ObjectMapper().writeValueAsBytes(reading))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isUnauthorized());
    }

    private StreetPostingBox makeStreetPostingBox() {
        StreetPostingBox spb = new StreetPostingBox();
        spb.setImei("IMEI01234567890");
        spb.setApiKey(UUID.randomUUID().toString());
        spb.setVersion(15);
        return spb;
    }

    private RemoteConfiguration makeRemoteConfiguration() {
        RemoteConfiguration rc = new RemoteConfiguration(13);
        rc.getProperties().setProperty("property", "value");
        return rc;
    }
}
