package io.phasetwo.keycloak.ext.event;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import io.phasetwo.keycloak.ext.util.Emails;
import lombok.extern.jbosslog.JBossLog;
import org.keycloak.email.EmailException;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

/** */
@JBossLog
@AutoService(EventListenerProviderFactory.class)
public class WelcomeEmail extends AbstractEventListenerProviderFactory {

  @Override
  public RegistrationEventListenerProvider create(KeycloakSession session) {
    return new RegistrationEventListenerProvider(session) {
      @Override
      public void onRegistration(RealmModel realm, UserModel user, Event event) {
        sendEmail(session, realm, user);
      }
    };
  }

  void sendEmail(KeycloakSession session, RealmModel realm, UserModel user) {
    try {
      Emails.sendEmail(
          "welcome-email.ftl",
          "welcomeEmailSubject",
          ImmutableList.of(),
          Maps.newHashMap(),
          session,
          realm,
          user);
    } catch (EmailException e) {
      log.warn("Failed to send welcome mail", e);
    }
  }

  @Override
  public String getId() {
    return "ext-event-welcome-email";
  }
}
