package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rog.domain.KeyRiskIndicatorPurposes;

@Repository
public interface KeyRiskIndicatorPurposesRepository extends JpaRepository<KeyRiskIndicatorPurposes, Long> {

}
