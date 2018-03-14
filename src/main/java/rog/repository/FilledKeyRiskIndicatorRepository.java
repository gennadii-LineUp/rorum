package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rog.domain.FilledKeyRiskIndicator;

@Repository
public interface FilledKeyRiskIndicatorRepository extends JpaRepository<FilledKeyRiskIndicator, Long> {

}
