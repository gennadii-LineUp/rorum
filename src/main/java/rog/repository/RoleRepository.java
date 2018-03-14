package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rog.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
}
