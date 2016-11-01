package au.com.auspost.smartspb.service;

import au.com.auspost.smartspb.dao.RemoteConfigurationDao;
import au.com.auspost.smartspb.domain.RemoteConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RemoteConfigurationService {
    @Autowired
    private RemoteConfigurationDao remoteConfigurationDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    public RemoteConfiguration load() {
        return remoteConfigurationDao.load();
    }
}
