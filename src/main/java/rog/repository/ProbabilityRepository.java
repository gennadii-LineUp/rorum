package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rog.domain.Probability;

import java.util.List;

@Repository
public interface ProbabilityRepository extends JpaRepository<Probability, Integer>{

    @Query("SELECT MAX(p.number) FROM Probability p")
    Integer getMaxValue();

    List<Probability> findAllByNumber(Integer number);
}
