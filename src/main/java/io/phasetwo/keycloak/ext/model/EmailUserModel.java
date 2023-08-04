package io.phasetwo.keycloak.ext.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.keycloak.models.*;

/**
 * Stupid way to send an email using EmailTemplateProvider when you don't have a user in the db for
 * theemail you want to use. Covers all the get methods from {@link
 * org.keycloak.email.freemarker.beans.ProfileBean}
 */
public class EmailUserModel implements UserModel {

  private final String email;
  private final String username;
  private final String firstName;
  private final String lastName;
  private final Map<String, List<String>> attributes;

  public EmailUserModel(String email) {
    this(email, null, null, null, null);
  }

  public EmailUserModel(
      String email,
      String username,
      String firstName,
      String lastName,
      Map<String, List<String>> attributes) {
    this.email = email;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.attributes = attributes;
  }

  @Override
  public String getId() {
    return null;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public void setUsername(String username) {}

  @Override
  public Long getCreatedTimestamp() {
    return null;
  }

  @Override
  public void setCreatedTimestamp(Long timestamp) {}

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public void setEnabled(boolean enabled) {}

  @Override
  public void setSingleAttribute(String name, String value) {}

  @Override
  public void setAttribute(String name, List<String> values) {}

  @Override
  public void removeAttribute(String name) {}

  @Override
  public String getFirstAttribute(String name) {
    return null;
  }

  @Override
  public Stream<String> getAttributeStream(final String name) {
    return null;
  }

  @Override
  public Map<String, List<String>> getAttributes() {
    return attributes;
  }

  @Override
  public Stream<String> getRequiredActionsStream() {
    return null;
  }

  @Override
  public void addRequiredAction(String action) {}

  @Override
  public void removeRequiredAction(String action) {}

  @Override
  public String getFirstName() {
    return firstName;
  }

  @Override
  public void setFirstName(String firstName) {}

  @Override
  public String getLastName() {
    return lastName;
  }

  @Override
  public void setLastName(String lastName) {}

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {}

  @Override
  public boolean isEmailVerified() {
    return true;
  }

  @Override
  public void setEmailVerified(boolean verified) {}

  @Override
  public Stream<GroupModel> getGroupsStream() {
    return null;
  }

  @Override
  public void joinGroup(GroupModel group) {}

  @Override
  public void leaveGroup(GroupModel group) {}

  @Override
  public boolean isMemberOf(GroupModel group) {
    return false;
  }

  @Override
  public String getFederationLink() {
    return null;
  }

  @Override
  public void setFederationLink(String link) {}

  @Override
  public String getServiceAccountClientLink() {
    return null;
  }

  @Override
  public void setServiceAccountClientLink(String clientInternalId) {}

  @Override
  public SubjectCredentialManager credentialManager() {
    return null;
  }

  @Override
  public Stream<RoleModel> getRealmRoleMappingsStream() {
    return null;
  }

  @Override
  public Stream<RoleModel> getClientRoleMappingsStream(ClientModel app) {
    return null;
  }

  @Override
  public boolean hasRole(RoleModel role) {
    return false;
  }

  @Override
  public void grantRole(RoleModel role) {}

  @Override
  public Stream<RoleModel> getRoleMappingsStream() {
    return null;
  }

  @Override
  public void deleteRoleMapping(RoleModel role) {}
}
