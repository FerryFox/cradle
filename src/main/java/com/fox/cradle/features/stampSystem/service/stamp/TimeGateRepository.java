package com.fox.cradle.features.stampSystem.service.stamp;

import com.fox.cradle.features.stampSystem.model.stamp.TimeGateSecurity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeGateRepository extends JpaRepository<TimeGateSecurity, Long>
{

}
