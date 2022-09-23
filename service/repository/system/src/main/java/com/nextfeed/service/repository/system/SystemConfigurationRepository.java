package com.nextfeed.service.repository.system;

import com.nextfeed.library.core.entity.system.SystemConfiguration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemConfigurationRepository extends CrudRepository<SystemConfiguration,Integer> {
    @Query("SELECT s FROM SystemConfiguration s WHERE s.name like ?1")
    List<SystemConfiguration> findAllByName(String name);
}
