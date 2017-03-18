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
public class RemoteConfigurationRepositoryIT {

    @Autowired
    private RemoteConfigurationCrudRepository remoteConfigurationCrudRepository;

    @Test
    public void testLoad() {
        RemoteConfiguration rc = remoteConfigurationCrudRepository.findOne(1);

        assertThat(rc.getVersion(), is(1));
        assertThat(rc.getProperties().size(), is(3));
    }
}
