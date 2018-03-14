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
import rog.domain.OrganisationStructure;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
@Transactional
public class OrganisationStructureServiceUnitTest {

    @Autowired
    private OrganisationStructureService organisationStructureService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final String USER_LOGIN = "user1B";
    private static final String USER_PASSWORD = "user";

    private static final Long OS_B = 1170L;
    private static final Long OS_WGO = 1166L;
    private static final Long RECEIVER = 44L;

    @Before
    public void setup() {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(USER_LOGIN, USER_PASSWORD);
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void getOneByCurrentUserTest(){
        OrganisationStructure organisationStructure =
            userService.getCurrentUser().getOrganisationStructure();
        assertThat(organisationStructure).
            isEqualToComparingOnlyGivenFields(organisationStructureService.getOneByCurrentUser());
    }

    @Test
    public void getListOfPossibleSenderOrganisationsTest(){
        OrganisationStructure senderOsB = organisationStructureService.findOne(OS_B);
        OrganisationStructure senderOsWGO = organisationStructureService.findOne(OS_WGO);
        OrganisationStructure receiver = organisationStructureService.findOne(RECEIVER);

        Long receiverId = receiver.getId();

        List<OrganisationStructure> expectedSenders =
            organisationStructureService.getListOfPossibleSenderOrganisations(receiverId);

        assertThat(expectedSenders).contains(senderOsB, senderOsWGO);
    }
}
