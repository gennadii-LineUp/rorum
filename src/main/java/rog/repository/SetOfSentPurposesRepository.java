package rog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rog.domain.SetOfSentPurposes;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the SetOfSentPurposes entity.
 */

@Repository
public interface SetOfSentPurposesRepository extends JpaRepository<SetOfSentPurposes, Long> {

    @Query("SELECT ssp FROM SetOfSentPurposes ssp JOIN ssp.user.organisationStructure o " +
        "WHERE o.id IN (:listIds) AND ssp.orders.id = :orderId AND ssp.isLastVersion = TRUE")
    List<SetOfSentPurposes> getAllByOrganisationIdsAndOrderId(@Param("listIds") List<Long> organisationStructuresIds,
                                                              @Param("orderId") Long orderId);

    @Query("SELECT ssp FROM SetOfSentPurposes ssp WHERE ssp.user.login = ?#{principal.username}")
    List<SetOfSentPurposes> findByCurrentUser();

    @Query("SELECT DISTINCT ssp FROM SetOfSentPurposes ssp LEFT JOIN FETCH ssp.glossaryOfPurposes")
    List<SetOfSentPurposes> findAllWithEagerRelationships();

    @Query("SELECT ssp FROM SetOfSentPurposes ssp LEFT JOIN FETCH ssp.glossaryOfPurposes WHERE ssp.id = :id")
    SetOfSentPurposes findOneWithEagerRelationships(@Param("id") Long id);

    @Query("SELECT ssp FROM SetOfSentPurposes ssp " +
        "WHERE ssp.user.organisationStructure.id = :organisationId AND " +
        "ssp.orders.id = :orderId AND ssp.isLastVersion = TRUE AND " +
        "(ssp.statusOfSending = 'CONFIRMED_PURPOSES' OR ssp.statusOfSending = 'CONFIRMED_PLAN')")
    Optional<SetOfSentPurposes> getOneSavedByOrganisationAndOrderIds(@Param("organisationId") Long organisationId,
                                                                     @Param("orderId") Long orderId);

    @Query("SELECT ssp FROM SetOfSentPurposes ssp " +
        "WHERE ssp.user.organisationStructure.id = :organisationId AND " +
        "ssp.orders.id = :orderId AND ssp.isLastVersion = TRUE")
    SetOfSentPurposes getOneUnsavedByOrganisationAndOrderIds(@Param("organisationId") Long organisationId,
                                                             @Param("orderId") Long orderId);

    @Modifying
    @Query("UPDATE SetOfSentPurposes ssp SET ssp.isLastVersion = FALSE " +
        "WHERE ssp.user.id IN (SELECT u.id FROM User u " +
        "WHERE u.organisationStructure.id = :#{#set.user.organisationStructure.id}) " +
        "AND ssp.orders.id = :#{#set.orders.id}")
    void setlastVersionToFalse(@Param("set") SetOfSentPurposes setOfSentPurpose);

    SetOfSentPurposes findOneByOrdersIdAndUserId(Long orderId, Long userId);

    SetOfSentPurposes findAllByOrdersIdAndUserIdAndIsLastVersion(Long orderId, Long userId, Boolean isisLastVersion);

    @Query("SELECT CASE WHEN (COUNT(gmu) > 0) THEN true ELSE false END " +
        "FROM SetOfSentPurposes ssp JOIN ssp.glossaryOfPurposes gp " +
        "JOIN gp.glossaryOfMeasureUnits gmu WHERE ssp.id = :sspId")
    boolean isExistMeasureUnits(@Param("sspId") Long setOfSentPurposesId);

    @Query("SELECT CASE WHEN (COUNT(gr) > 0) THEN true ELSE false END " +
        "FROM SetOfSentPurposes ssp JOIN ssp.glossaryOfPurposes gp " +
        "JOIN gp.glossaryOfRisks gr WHERE ssp.id = :sspId")
    boolean isExistRisks(@Param("sspId") Long setOfSentPurposesId);

    @Query("SELECT CASE WHEN (COUNT(gcr) > 0) THEN true ELSE false END " +
        "FROM SetOfSentPurposes ssp JOIN ssp.user u JOIN u.organisationStructure os " +
        "JOIN os.glossaryOfCommercialRisks gcr WHERE ssp.id = :sspId")
    boolean isExistCommercialRisks(@Param("sspId") Long setOfSentPurposesId);

    @Query("SELECT CASE WHEN (COUNT(gkri) > 0) THEN true ELSE false END " +
        "FROM SetOfSentPurposes ssp JOIN ssp.glossaryOfPurposes gp " +
        "JOIN gp.glossaryOfKeyRiskIndicators gkri WHERE ssp.id = :sspId")
    boolean isExistRiskKeyIndicator(@Param("sspId") Long setOfSentPurposesId);

}
