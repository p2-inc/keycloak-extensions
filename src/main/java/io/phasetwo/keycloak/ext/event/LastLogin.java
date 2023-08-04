package io.phasetwo.keycloak.ext.event;

import static io.phasetwo.keycloak.ext.util.Io.*;

import com.google.auto.service.AutoService;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import lombok.extern.jbosslog.JBossLog;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.events.EventType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.KeycloakModelUtils;

/** */
@JBossLog
@AutoService(EventListenerProviderFactory.class)
public class LastLogin extends AbstractEventListenerProviderFactory {

  public static final String LAST_LOGIN = "last-login";

  @Override
  public EventListenerProvider create(final KeycloakSession session) {
    return new AbstractEventListenerProvider() {
      @Override
      public void onEvent(final Event event) {
        if (EventType.LOGIN.equals(event.getType())) {
          RealmModel realm = Events.getRealm(session, event);
          UserModel user = Events.getUser(session, event);
          if (user != null) {
            KeycloakModelUtils.runJobInTransaction(
                session.getKeycloakSessionFactory(),
                s -> {
                  UserModel u = Events.getUser(s, event);
                  updateLastLogin(u);
                });
          }
        }
      }
    };
  }

  private static final String RESPONSE_MODE_KEY = "response_mode";

  private boolean isInitialLogin(Event event) {
    if (event.getDetails() != null) {
      if (event.getDetails().get(RESPONSE_MODE_KEY) != null
          && "query".equals(event.getDetails().get(RESPONSE_MODE_KEY))) {
        return true;
      }
    }
    return false;
  }

  private void updateLastLogin(UserModel u) {
    try {
      log.debugf("Updating last login status for user: %s", u.getUsername());
      // Use current server time for login event
      OffsetDateTime loginTime = OffsetDateTime.now(ZoneOffset.UTC);
      String loginTimeS = DateTimeFormatter.ISO_DATE_TIME.format(loginTime);
      u.setSingleAttribute(LAST_LOGIN, loginTimeS);
    } catch (Exception e) {
      log.warn("Error updating last-login", e);
    }
  }

  /** Returns true if the user has logged in within the given number of days */
  public static boolean loginSince(UserModel u, int days) {
    String dateString = u.getFirstAttribute(LAST_LOGIN);
    try {
      Instant instant = Instant.parse(dateString);
      LocalDate date = instant.atZone(ZoneId.systemDefault()).toLocalDate();
      LocalDate today = LocalDate.now();
      long daysDifference = ChronoUnit.DAYS.between(date, today);
      return (daysDifference < days);
    } catch (Exception e) {
      log.warnf("error parsing last login date %s %s", dateString, e.getMessage());
      return false;
    }
  }

  @Override
  public String getId() {
    return "ext-event-last-login";
  }
}
