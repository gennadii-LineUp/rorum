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
import rog.domain.SetOfSentPurposes;
import rog.domain.User;
import rog.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
@Transactional
public class SetOfSentPurposesServiceUnitTest {

    @Autowired
    private SetOfSentPurposesService setOfSentPurposesService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganisationStructureService organisationStructureService;

    @Autowired
    private OrdersService ordersService;

    private final static String PARENTED_LOGIN = "user1B";
    private final static String SUPERVISING_LOGIN = "userSchronisko";
    private final static String ADMIN_LOGIN = "adminB";
    private final static String ADMIN_PASSWORD = "admin";

    private final static Long ORDER_ID = 1L;

    private SetOfSentPurposes setOfSentPurposesOfParentedUser;

    private SetOfSentPurposes setOfSentPurposesOfSupervisingUser;

    @Before
    public void setup(){
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(ADMIN_LOGIN, ADMIN_PASSWORD);
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        setOfSentPurposesOfParentedUser = new SetOfSentPurposes();
        setOfSentPurposesOfParentedUser.setUser(userRepository.findOneByLogin(PARENTED_LOGIN).get());
        setOfSentPurposesOfParentedUser.setOrders(ordersService.findOne(ORDER_ID));
        setOfSentPurposesService.create(setOfSentPurposesOfParentedUser);

        setOfSentPurposesOfSupervisingUser = new SetOfSentPurposes();
        setOfSentPurposesOfSupervisingUser.setUser(userRepository.findOneByLogin(SUPERVISING_LOGIN).get());
        setOfSentPurposesOfSupervisingUser.setOrders(ordersService.findOne(ORDER_ID));
        setOfSentPurposesService.create(setOfSentPurposesOfSupervisingUser);
    }

    @Test
    public void getAllByParentedOrganisationsAndOrderIdTest(){
        Long organisationId = organisationStructureService.getOneByCurrentUser().getId();
        List<SetOfSentPurposes> setOfSentPurposes =
            setOfSentPurposesService.getAllByParentedOrganisationsAndOrderId(organisationId, ORDER_ID);

        assertThat(setOfSentPurposesOfParentedUser).isIn(setOfSentPurposes);
    }

    @Test
    public void getAllBySupervisingOrganisationsAndOrderIdTest(){
        Long organisationId = organisationStructureService.getOneByCurrentUser().getId();
        List<SetOfSentPurposes> setOfSentPurposes =
            setOfSentPurposesService.getAllBySupervisingOrganisationsAndOrderId(organisationId, ORDER_ID);

        assertThat(setOfSentPurposesOfSupervisingUser).isIn(setOfSentPurposes);
    }

    @Test
    public void getOneByOrganisationIdAndOrderIdTest(){
        User user = userRepository.findOneByLogin(PARENTED_LOGIN).get();
        Long organisationId = user.getOrganisationStructure().getId();

        SetOfSentPurposes expectedSetOfSentPurposes =
            setOfSentPurposesService.getOneByOrganisationIdAndOrderId(organisationId, ORDER_ID);
        assertThat(setOfSentPurposesOfParentedUser).isEqualTo(expectedSetOfSentPurposes);
    }


}
