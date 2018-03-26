package rog.service.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import rog.config.Constants;
import rog.domain.Authority;
import rog.domain.OrganisationStructure;
import rog.domain.Role;
import rog.domain.User;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO implements Serializable{

    private Long id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    @Size(max = 256)
    private String imageUrl;

    private boolean activated = false;

    @Size(min = 2, max = 6)
    private String langKey;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Set<Long> roles = new HashSet<>();

    private Set<String> authorities = new HashSet<>();

	private Long organisationStructureId;

    public UserDTO() {
    }

    public UserDTO(User user) {

        if(Objects.isNull(user)){
            return;
        }

        this.id = user.getId();
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.activated = user.getActivated();
        this.imageUrl = user.getImageUrl();
        this.langKey = user.getLangKey();
        this.createdBy = user.getCreatedBy();
        this.createdDate = user.getCreatedDate();
        this.lastModifiedBy = user.getLastModifiedBy();
        this.lastModifiedDate = user.getLastModifiedDate();

        this.authorities = user.getRoles()
            .stream()
            .flatMap(r -> r.getAuthorities().stream())
            .map(Authority::getName)
            .collect(Collectors.toSet());

        this.roles = user.getRoles()
            .stream()
            .map(Role::getId)
            .collect(Collectors.toSet());

        OrganisationStructure organisationStructure = user.getOrganisationStructure();
        if(Objects.nonNull(organisationStructure)){
            this.organisationStructureId = organisationStructure.getId();
        }

    }

    public UserDTO(Long id, String firstName, String lastName,
                   String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public UserDTO(Long id, String login, String firstName, String lastName,
                   String email, boolean activated, String imageUrl, String langKey,
                   String createdBy, Instant createdDate, String lastModifiedBy, Instant lastModifiedDate,
                   Set<String> authorities, Set<Long> roles, Long organisationStructureId) {

        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.activated = activated;
        this.imageUrl = imageUrl;
        this.langKey = langKey;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.authorities = authorities;
        this.roles = roles;
        this.organisationStructureId = organisationStructureId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public Set<Long> getRoles() {
        return roles;
    }

    public void setRoles(Set<Long> roles) {
        this.roles = roles;
    }

    public Long getOrganisationStructureId() {
        return organisationStructureId;
    }

    public void setOrganisationStructureId(Long organisationStructureId) {
        this.organisationStructureId = organisationStructureId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return activated == userDTO.activated &&
            Objects.equals(id, userDTO.id) &&
            Objects.equals(login, userDTO.login) &&
            Objects.equals(firstName, userDTO.firstName) &&
            Objects.equals(lastName, userDTO.lastName) &&
            Objects.equals(email, userDTO.email) &&
            Objects.equals(imageUrl, userDTO.imageUrl) &&
            Objects.equals(langKey, userDTO.langKey) &&
            Objects.equals(createdBy, userDTO.createdBy) &&
            Objects.equals(createdDate, userDTO.createdDate) &&
            Objects.equals(lastModifiedBy, userDTO.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, userDTO.lastModifiedDate) &&
            Objects.equals(roles, userDTO.roles) &&
            Objects.equals(authorities, userDTO.authorities) &&
            Objects.equals(organisationStructureId, userDTO.organisationStructureId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, login, firstName, lastName, email, imageUrl, activated, langKey, createdBy, createdDate, lastModifiedBy, lastModifiedDate, roles, authorities, organisationStructureId);
    }
}
