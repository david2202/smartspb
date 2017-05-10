package au.com.auspost.smartspb.dao;

import au.com.auspost.smartspb.domain.StreetPostingBox;
import org.springframework.data.repository.CrudRepository;

public interface StreetPostingBoxCrudRepository extends CrudRepository<StreetPostingBox, Integer> {
    StreetPostingBox findOneByImei(String imei);
}
