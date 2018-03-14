package rog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rog.domain.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(Instant dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByLogin(String login);

    @EntityGraph(attributePaths = "roles.authorities")
    User findOneWithAuthoritiesById(Long id);

    @EntityGraph(attributePaths = "roles.authorities")
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    Page<User> findAllByLoginNot(Pageable pageable, String login);

    @Query("SELECT u FROM User u WHERE (SELECT r.id FROM Role r WHERE r.name = 'ADMIN') " +
        "MEMBER u.roles AND u.organisationStructure.id = " +
        "(SELECT u.organisationStructure.id FROM User u WHERE u.login = ?#{principal.username})")
    User getLocalAdminOfCurrentOrganisation();

//    @Query("SELECT u FROM User u WHERE u = function('public.\"getAllParentedAndSupervisoredUsers\"', "+i+")")
//    ArrayList<User> getAllParentedAndSupervisoredUsers();
//    ArrayList<User> getAllParentedAndSupervisoredUsers(@Param("organisationStructureId") Long organisationStructureId);
//    @Query("SELECT U FROM User u WHERE organisation_structure_id = :organisationStructureId")

    @Query(value = "SELECT u.* FROM public.\"getAllParentedAndSupervisoredUsers\"(?1) u", nativeQuery = true)
    ArrayList<User> getAllParentedAndSupervisoredUsers(int organisationStructureId);

    User findOneById(Long id);
}
