package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rog.domain.GlossaryOfControlMechanisms;

@Repository
public interface GlossaryOfControlMechanismsRepository extends JpaRepository<GlossaryOfControlMechanisms, Long> {

}
