package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rog.domain.PowerOfInfluence;

import java.util.List;

@Repository
public interface PowerOfInfluenceRepository extends JpaRepository<PowerOfInfluence, Integer> {

    @Query("SELECT MAX(p.number) FROM PowerOfInfluence p")
    Integer getMaxValue();

    List<PowerOfInfluence> findAllByNumber(Integer number);
}
