package rog.service.mapper;

import org.springframework.stereotype.Service;
import rog.domain.Authority;
import rog.domain.OrganisationStructure;
import rog.domain.Role;
import rog.domain.User;
import rog.service.dto.UserDTO;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the entity User and its DTO called UserDTO.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserMapper {

    public UserDTO userDTO(User user) {
        return new UserDTO(user);
    }

    public List<UserDTO> usersToUserDTOs(List<User> users) {
        return users
            .stream()
            .filter(Objects::nonNull)
            .map(this::userDTO)
            .collect(Collectors.toList());
    }

    public User user(UserDTO userDTO) {

       if (Objects.isNull(userDTO)) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setLogin(userDTO.getLogin());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setImageUrl(userDTO.getImageUrl());
        user.setActivated(userDTO.isActivated());
        user.setLangKey(userDTO.getLangKey());

        Set<Role> roles = userDTO.getRoles()
            .stream()
            .map(Role::new)
            .collect(Collectors.toSet());
        user.setRoles(roles);

        Set<Authority> authorities = this.authoritiesFromStrings(userDTO.getAuthorities());
        if(authorities != null) {
            user.getRoles()
                .forEach(role -> role.setAuthorities(user.getRoles().stream()
                    .filter(rt -> rt.equals(role))
                    .flatMap(r -> r.getAuthorities().stream())
                    .collect(Collectors.toSet())));
        }

        OrganisationStructure organisationStructure = new OrganisationStructure();
        organisationStructure.setId(userDTO.getOrganisationStructureId());
        user.setOrganisationStructure(organisationStructure);

        return user;
    }

    private Set<Authority> authoritiesFromStrings(Set<String> strings) {
        return strings.stream().map(string -> {
            Authority auth = new Authority();
            auth.setName(string);
            return auth;
        }).collect(Collectors.toSet());
    }
}
