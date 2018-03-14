package rog.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rog.RorumApp;
import rog.domain.GlossaryOfPurposes;
import rog.domain.Incident;
import rog.domain.SetOfSentPurposes;
import rog.domain.enumeration.StatusOfSending;
import rog.repository.UserRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
@Transactional
public class IncidentServiceUnitTest {

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SetOfSentPurposesService setOfSentPurposesService;

    @Autowired
    private OrganisationStructureService organisationStructureService;

    @Autowired
    private OrdersService ordersService;

    private SetOfSentPurposes setOfSentPurposes;

    @Autowired
    private GlossaryOfPurposesService glossaryOfPurposesService;

    private static final String PARENT_LOGIN = "adminB";
    private static final String PARENT_PASSWORD = "admin";

    private static final String PARENTED_LOGIN = "user1B";
    private static final String PARENTED_PASSWORD = "user";

    private static final String SUPERVISOR_LOGIN = "adminB";
    private static final String SUPERVISOR_PASSWORD = "admin";

    private static final String SUPERVISING_LOGIN = "userSchronisko";
    private static final String SUPERVISING_PASSWORD = "user";

    private static final Long ORDER_ID = 1L;
    private static final Long PURPOSE_ID = 1L;

    @Before
    public void setup(){
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(PARENT_LOGIN, PARENT_PASSWORD);
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        setOfSentPurposes = new SetOfSentPurposes();
        setOfSentPurposes.setUser(userRepository.findOneByLogin(PARENT_LOGIN).get());
        setOfSentPurposes.setStatusOfSending(StatusOfSending.CONFIRMED_PLAN);
        setOfSentPurposes.setOrders(ordersService.findOne(ORDER_ID));
        GlossaryOfPurposes glossaryOfPurposes = glossaryOfPurposesService.findOne(PURPOSE_ID);
        setOfSentPurposes.setGlossaryOfPurposes(new HashSet<>(Collections.singletonList(glossaryOfPurposes)));
        setOfSentPurposesService.create(setOfSentPurposes);

    }

    @Test
    public void getAllOfParentedOrganisationTest(){
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(PARENTED_LOGIN, PARENTED_PASSWORD);
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Incident incidentOfParentedUser = new Incident();
        incidentOfParentedUser.setSetOfSentPurposes(setOfSentPurposes);
        incidentService.save(incidentOfParentedUser);

        authenticationToken =
            new UsernamePasswordAuthenticationToken(PARENT_LOGIN, PARENT_PASSWORD);
        authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Long organisationId = organisationStructureService.getOneByCurrentUser().getId();
        List<Incident> incidents = incidentService.getAllOfParentedOrganisation(organisationId);

        assertThat(incidentOfParentedUser).isIn(incidents);
    }


    @Test
    public void getAllOfSupervisingOrganisationTest(){
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(SUPERVISING_LOGIN, SUPERVISING_PASSWORD);
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Incident incidentOfSupervisingUser = new Incident();
        incidentOfSupervisingUser.setSetOfSentPurposes(setOfSentPurposes);
        incidentService.save(incidentOfSupervisingUser);

        authenticationToken =
            new UsernamePasswordAuthenticationToken(SUPERVISOR_LOGIN, SUPERVISOR_PASSWORD);
        authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Long organisationId = organisationStructureService.getOneByCurrentUser().getId();
        List<Incident> incidents = incidentService.getAllOfSupervisingOrganisation(organisationId);

        assertThat(incidentOfSupervisingUser).isIn(incidents);
    }
}
