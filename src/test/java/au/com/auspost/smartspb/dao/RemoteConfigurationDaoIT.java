package au.com.auspost.smartspb.dao;

import au.com.auspost.smartspb.domain.RemoteConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RemoteConfigurationDaoIT {

    @Autowired
    private RemoteConfigurationDao remoteConfigurationDao;

    @Test
    public void testLoad() {
        RemoteConfiguration rc = remoteConfigurationDao.load();

        assertThat(rc.getVersion(), is(1));
        assertThat(rc.getProperties().size(), is(3));

        assertThat(rc.getProperties().getProperty("url"), is("http://localhost:8080/rest/remote"));
        assertThat(rc.getProperties().getProperty("readingSeconds"), is("8"));
        assertThat(rc.getProperties().getProperty("sleepSeconds"), is("8"));
    }
}
