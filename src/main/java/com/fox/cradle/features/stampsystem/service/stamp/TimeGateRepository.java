package com.fox.cradle.features.stampsystem.service.stamp;

import com.fox.cradle.features.stampsystem.model.stamp.TimeGateSecurity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeGateRepository extends JpaRepository<TimeGateSecurity, Long>
{

}
