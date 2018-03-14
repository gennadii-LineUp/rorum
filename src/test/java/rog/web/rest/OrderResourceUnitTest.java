package rog.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rog.RorumApp;
import rog.domain.GlossaryOfPurposes;
import rog.domain.SetOfSentPurposes;
import rog.domain.User;
import rog.domain.enumeration.StatusOfSending;
import rog.repository.UserRepository;
import rog.service.GlossaryOfPurposesService;
import rog.service.OrdersService;
import rog.service.SetOfSentPurposesService;
import rog.service.dto.SetOfSentPurposesDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class OrderResourceUnitTest {

    private MockMvc restOrdersMockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private SetOfSentPurposesDTO setOfSentPurposesDTO;

    @Autowired
    private SetOfSentPurposesService setOfSentPurposesService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GlossaryOfPurposesService glossaryOfPurposesService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final String USER_LOGIN = "user1B";
    private static final String USER_PASSWORD = "user";

    private static final String ADMIN_LOGIN = "adminB";
    private static final String ADMIN_PASSWORD = "admin";

    private static final Long ORDER_ID = 1L;

    private List<Long> glossaryOfPurposesIdsList;

    private SetOfSentPurposes setOfConfirmedPurposes;

    private static final String NOTATION = "notation";

    @Before
    public void setup(){

        MockitoAnnotations.initMocks(this);
        this.restOrdersMockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        User user = userRepository.findOneByLogin(USER_LOGIN).get();

        Long organisationId = user.getOrganisationStructure().getId();

        glossaryOfPurposesIdsList = glossaryOfPurposesService.getAllByOrganisationId(organisationId).stream()
        .map(GlossaryOfPurposes::getId).collect(Collectors.toList());

        login(USER_LOGIN, USER_PASSWORD);

        setOfConfirmedPurposes = new SetOfSentPurposes();
        setOfConfirmedPurposes.setOrders(ordersService.findOne(ORDER_ID));
        setOfConfirmedPurposes.setUser(userRepository.findOneByLogin(USER_LOGIN).get());
        setOfConfirmedPurposes.setStatusOfSending(StatusOfSending.CONFIRMED_PURPOSES);
        setOfConfirmedPurposes.setGlossaryOfPurposes(new HashSet<>(glossaryOfPurposesService.getAllByOrganisationId(organisationId)));
        setOfSentPurposesService.create(setOfConfirmedPurposes);
    }

    @Test
    public void createInvalidSetOfSentPurposes() throws Exception {
        login(USER_LOGIN, USER_PASSWORD);

        setOfSentPurposesDTO = new SetOfSentPurposesDTO();
        setOfSentPurposesDTO.setStatusOfSending(StatusOfSending.UNCHECKED_PURPOSES);

        restOrdersMockMvc.perform(post("/api/orders/{ordersId}/set_of_sent_purposes", ORDER_ID)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(setOfSentPurposesDTO)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void createValidSetOfSentPurposes() throws Exception {
        login(USER_LOGIN, USER_PASSWORD);

        setOfSentPurposesDTO = new SetOfSentPurposesDTO();
        setOfSentPurposesDTO.setStatusOfSending(StatusOfSending.UNCHECKED_PURPOSES);

        Long purposesId = glossaryOfPurposesIdsList.get(1);
        Set<Long> purposeIds = new HashSet<>();
        purposeIds.add(purposesId);
        setOfSentPurposesDTO.setIds(purposeIds);

        restOrdersMockMvc.perform(post("/api/orders/{ordersId}/set_of_sent_purposes", ORDER_ID)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(setOfSentPurposesDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void updateInvalidSetOfSentPurposesWithId() throws Exception {
        login(USER_LOGIN, USER_PASSWORD);

        setOfSentPurposesDTO = new SetOfSentPurposesDTO();
        setOfSentPurposesDTO.setId(1L);

        restOrdersMockMvc.perform(put("/api/orders/{ordersId}/set_of_sent_purposes", ORDER_ID)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(setOfSentPurposesDTO)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateInvalidPlan() throws Exception {
        login(USER_LOGIN, USER_PASSWORD);

        setOfSentPurposesDTO = new SetOfSentPurposesDTO();
        setOfSentPurposesDTO.setId(setOfConfirmedPurposes.getId());
        setOfSentPurposesDTO.setStatusOfSending(StatusOfSending.UNCHECKED_PLAN);

        restOrdersMockMvc.perform(put("/api/orders/{ordersId}/set_of_sent_purposes", ORDER_ID)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(setOfSentPurposesDTO)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void acceptSetOfSentPurposes() throws Exception {
        login(ADMIN_LOGIN, ADMIN_PASSWORD);

        setOfSentPurposesDTO = new SetOfSentPurposesDTO();
        setOfSentPurposesDTO.setId(setOfConfirmedPurposes.getId());
        setOfSentPurposesDTO.setStatusOfSending(StatusOfSending.CONFIRMED_PURPOSES);

        restOrdersMockMvc.perform(put("/api/orders/{ordersId}/set_of_sent_purposes", ORDER_ID)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(setOfSentPurposesDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void rejectInvalidSetOfSentPurposes() throws Exception {
        login(ADMIN_LOGIN, ADMIN_PASSWORD);

        setOfSentPurposesDTO = new SetOfSentPurposesDTO();
        setOfSentPurposesDTO.setId(setOfConfirmedPurposes.getId());
        setOfSentPurposesDTO.setStatusOfSending(StatusOfSending.REJECTED_PURPOSES);

        restOrdersMockMvc.perform(put("/api/orders/{ordersId}/set_of_sent_purposes", ORDER_ID)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(setOfSentPurposesDTO)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void rejectSetOfSentPurposes() throws Exception {
        login(ADMIN_LOGIN, ADMIN_PASSWORD);

        setOfSentPurposesDTO = new SetOfSentPurposesDTO();
        setOfSentPurposesDTO.setId(setOfConfirmedPurposes.getId());
        setOfSentPurposesDTO.setStatusOfSending(StatusOfSending.REJECTED_PURPOSES);
        setOfSentPurposesDTO.setNotation(NOTATION);

        restOrdersMockMvc.perform(put("/api/orders/{ordersId}/set_of_sent_purposes", ORDER_ID)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(setOfSentPurposesDTO)))
            .andExpect(status().isOk());
    }

    @Test
    public void savePlan() throws Exception {
        login(ADMIN_LOGIN, ADMIN_PASSWORD);

        setOfSentPurposesDTO = new SetOfSentPurposesDTO();
        setOfSentPurposesDTO.setId(setOfConfirmedPurposes.getId());
        setOfSentPurposesDTO.setStatusOfSending(StatusOfSending.CONFIRMED_PURPOSES);

        restOrdersMockMvc.perform(put("/api/orders/{ordersId}/set_of_sent_purposes", ORDER_ID)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(setOfSentPurposesDTO)));

        login(USER_LOGIN, USER_PASSWORD);

        setOfConfirmedPurposes = setOfSentPurposesService.findOne(setOfConfirmedPurposes.getId());

        setOfSentPurposesDTO = new SetOfSentPurposesDTO(setOfConfirmedPurposes);

        restOrdersMockMvc.perform(put("/api/orders/{ordersId}/set_of_sent_purposes", ORDER_ID)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(setOfSentPurposesDTO)))
            .andExpect(status().isOk());
    }

    private void login(String login, String password){
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(login, password);
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
