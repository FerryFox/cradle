package com.fox.cradle.features.stampsystem.service.stamp;

import com.fox.cradle.features.stampsystem.model.stamp.StampInternalSecurity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeGateRepository extends JpaRepository<StampInternalSecurity, Long>
{

}
