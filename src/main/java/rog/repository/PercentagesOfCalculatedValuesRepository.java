package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rog.domain.PercentagesOfCalculatedValues;

@Repository
public interface PercentagesOfCalculatedValuesRepository extends JpaRepository<PercentagesOfCalculatedValues, Integer> {

}
