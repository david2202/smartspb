package au.com.auspost.smartspb.web.controller.rest.remote;

import au.com.auspost.smartspb.domain.Reading;
import au.com.auspost.smartspb.domain.RemoteConfiguration;
import au.com.auspost.smartspb.domain.RemoteConfigurationProperty;
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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @MockBean
    private SimpMessagingTemplate messagingTemplate;


    @Test
    public void testPost() throws Exception {
        StreetPostingBox spb = makeStreetPostingBox();
        RemoteConfiguration remoteConfiguration = makeRemoteConfiguration();

        ReadingVO reading = new ReadingVO();
        reading.setGrams(15);
        reading.setDegreesC(Temperature.valueOf("21.5"));

        List<ReadingVO> readings = new ArrayList<>();
        readings.add(reading);

        System.out.println(new ObjectMapper().writeValueAsString(readings));

        when(streetPostingBoxService.load(spb.getImei())).thenReturn(spb);
        when(remoteConfigurationService.load()).thenReturn(remoteConfiguration);

        mvc.perform(post("/rest/remote/spb/" + spb.getImei() + "/reading")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("apiKey", spb.getApiKey())
                .content(new ObjectMapper().writeValueAsBytes(readings))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.configVersion", is(remoteConfiguration.getVersion())))
                .andExpect(jsonPath("$.spbVersion", is(spb.getVersion())));
        verify(streetPostingBoxService).load(spb.getImei());
        verify(streetPostingBoxService).save(any(StreetPostingBox.class));
        verify(remoteConfigurationService).load();
        verify(messagingTemplate).convertAndSend(eq("/topic/readingsUpdate"), any(List.class));
    }

    @Test
    public void testPostUnauthorised() throws Exception {
        StreetPostingBox spb = makeStreetPostingBox();

        ReadingVO reading = new ReadingVO();
        reading.setGrams(15);
        reading.setDegreesC(Temperature.valueOf("21.5"));

        List<ReadingVO> readings = new ArrayList<>();
        readings.add(reading);

        when(streetPostingBoxService.load(spb.getImei())).thenReturn(spb);

        mvc.perform(post("/rest/remote/spb/" + spb.getImei() + "/reading")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("apiKey", "somerandomekey")
                .content(new ObjectMapper().writeValueAsBytes(readings))
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isUnauthorized());
        verify(streetPostingBoxService).load(spb.getImei());
        verify(readingService, never()).save(any(Reading.class));
        verify(remoteConfigurationService, never()).load();
        verify(messagingTemplate, never()).convertAndSend(any(), any(List.class));
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
        rc.addProperty(new RemoteConfigurationProperty("property", "value"));
        return rc;
    }
}
