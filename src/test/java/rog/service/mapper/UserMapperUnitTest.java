package rog.service.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rog.RorumApp;
import rog.domain.Role;
import rog.domain.User;
import rog.service.dto.UserDTO;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RorumApp.class)
public class UserMapperUnitTest {

    @Autowired
    private UserMapper userMapper;

    private User user;

    private UserDTO userDTO;

    private static final Long USER_ID = 1010L;
    private static final String USER_LOGIN = "user";
    private static final boolean USER_ACTIVATED = true;
    private static final String USER_EMAIL = "user";
    private static final String USER_FIRST_NAME = "user";
    private static final String USER_LAST_NAME = "user";
    private static final String USER_IMAGE_URL = "http://placehold.it/50x50";
    private static final String USER_LANG_KEY = "en";
    private static final Set<Role> roles = new HashSet<>();
    private static final Set<Long> rolesIds = new HashSet<>();

    @Before
    public void init(){
        roles.add(new Role(1L));
        roles.add(new Role(2L));

        rolesIds.add(1L);
        rolesIds.add(2L);

        user = new User();
        user.setId(USER_ID);
        user.setLogin(USER_LOGIN);
        user.setActivated(USER_ACTIVATED);
        user.setEmail(USER_EMAIL);
        user.setFirstName(USER_FIRST_NAME);
        user.setLastName(USER_LAST_NAME);
        user.setImageUrl(USER_IMAGE_URL);
        user.setLangKey(USER_LANG_KEY);
        user.setRoles(roles);

        userDTO = new UserDTO();
        userDTO.setId(USER_ID);
        userDTO.setLogin(USER_LOGIN);
        userDTO.setActivated(USER_ACTIVATED);
        userDTO.setEmail(USER_EMAIL);
        userDTO.setFirstName(USER_FIRST_NAME);
        userDTO.setLastName(USER_LAST_NAME);
        userDTO.setImageUrl(USER_IMAGE_URL);
        userDTO.setLangKey(USER_LANG_KEY);
        userDTO.setRoles(rolesIds);
    }

    @Test
    public void convertedUserDTOMustEqualUser(){
        User maybeUser = userMapper.user(userDTO);
        assertThat(maybeUser).isEqualToComparingOnlyGivenFields(user);
    }

    @Test
    public void userDTOMustEqualConvertedUser(){
        UserDTO maybeUserDTO = userMapper.userDTO(user);
        assertThat(maybeUserDTO).isEqualToComparingOnlyGivenFields(user);
    }

}
