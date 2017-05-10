package au.com.auspost.smartspb.service;

import au.com.auspost.smartspb.dao.StreetPostingBoxCrudRepository;
import au.com.auspost.smartspb.domain.StreetPostingBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StreetPostingBoxService {
    @Autowired
    private StreetPostingBoxCrudRepository streetPostingBoxCrudRepository;

    @Transactional(propagation = Propagation.SUPPORTS)
    public StreetPostingBox load(String imei) {
        return streetPostingBoxCrudRepository.findOneByImei(imei);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(StreetPostingBox spb) {
        streetPostingBoxCrudRepository.save(spb);
    }


}
