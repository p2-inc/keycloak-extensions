package io.phasetwo.keycloak.ext.event;

import lombok.extern.jbosslog.JBossLog;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

/** */
@JBossLog
public abstract class RegistrationEventListenerProvider implements EventListenerProvider {

  protected final KeycloakSession session;

  protected RegistrationEventListenerProvider(KeycloakSession session) {
    this.session = session;
  }

  public abstract void onRegistration(RealmModel realm, UserModel user, Event event);

  @Override
  public void onEvent(Event event) {
    if (EventType.REGISTER.equals(event.getType())) {
      RealmModel realm = session.realms().getRealm(event.getRealmId());
      UserModel user = session.users().getUserById(realm, event.getUserId());
      onRegistration(realm, user, event);
    } else {
      log.debugf("Skipping event %s", event.getType());
    }
  }

  @Override
  public void onEvent(AdminEvent adminEvent, boolean b) {}

  @Override
  public void close() {}
}
