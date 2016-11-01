package au.com.auspost.smartspb.dao;

import au.com.auspost.smartspb.domain.StreetPostingBox;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.TimeZone;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StreetPostingBoxDaoIT {
    @Autowired
    private StreetPostingBoxDao streetPostingBoxDao;

    @Test
    public void testLoadByImei() {
        StreetPostingBox spb = streetPostingBoxDao.load("IMEI12345678902");

        assertThat(spb.getImei(), is("IMEI12345678902"));
        assertThat(spb.getId(), is(1));
        assertThat(spb.getTimezone(), is(TimeZone.getTimeZone("Australia/Perth")));
        assertThat(spb.getApiKey(), is("16fa2ee7-6614-4f62-bc16-a3c6fa189675"));
        assertThat(spb.getPrevApiKey(), is("a73c5740-1cde-40a9-bde7-1d5e44761f77"));
        assertThat(spb.getVersion(), is(1));
    }
}
