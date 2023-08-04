package io.phasetwo.keycloak.ext.util;

import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public class Links {

  public static String adminConsoleUser(
      KeycloakSession session, RealmModel adminRealm, RealmModel realm, UserModel user) {
    return String.format(
        "%sadmin/%s/console/#/%s/users/%s",
        session.getContext().getAuthServerUrl().toString(),
        adminRealm.getName(),
        realm.getName(),
        user.getId());
  }

  public static String adminConsoleUser(KeycloakSession session, RealmModel realm, UserModel user) {
    return String.format(
        "%sadmin/master/console/#/%s/users/%s",
        session.getContext().getAuthServerUrl().toString(), realm.getName(), user.getId());
  }

  public static String adminConsoleClient(
      KeycloakSession session, RealmModel adminRealm, RealmModel realm, ClientModel client) {
    return String.format(
        "%sadmin/%s/console/#/%s/clients/%s",
        session.getContext().getAuthServerUrl().toString(),
        adminRealm.getName(),
        realm.getName(),
        client.getId());
  }

  public static String adminConsoleClient(
      KeycloakSession session, RealmModel realm, ClientModel client) {
    return String.format(
        "%sadmin/master/console/#/%s/clients/%s",
        session.getContext().getAuthServerUrl().toString(), realm.getName(), client.getId());
  }
}
