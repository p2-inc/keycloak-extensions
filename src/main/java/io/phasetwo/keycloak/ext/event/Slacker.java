package io.phasetwo.keycloak.ext.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import io.phasetwo.keycloak.ext.util.Io;
import java.util.Map;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserModel;
import org.keycloak.util.JsonSerialization;

public interface Slacker {

  default String webhookUrl() {
    return System.getenv("SLACK_WEBHOOK_URL");
  }

  default String env() {
    return System.getenv("PHASETWO_ENV");
  }

  default String name(UserModel user) {
    StringBuilder o = new StringBuilder();
    if (user.getFirstName() != null) {
      o.append(user.getFirstName());
    }
    if (user.getFirstName() != null && user.getLastName() != null) {
      o.append(" ");
    }
    if (user.getLastName() != null) {
      o.append(user.getLastName());
    }
    return o.toString();
  }

  @SuppressWarnings("deprecation")
  default SimpleHttp.Response send(String templatePath, Map values, KeycloakSession session)
      throws Exception {
    String template = Io.readFileFromClasspath(templatePath);
    StrSubstitutor sub = new StrSubstitutor(values);
    template = sub.replace(template);
    JsonNode node = JsonSerialization.mapper.readTree(template);
    return SimpleHttp.doPost(webhookUrl(), session).json(node).asResponse();
  }

  @SuppressWarnings("deprecation")
  default void error(String message, Throwable e, KeycloakSession session) {
    String realm = "[empty]";
    String stack = "[empty]";
    try {
      realm = session.getContext().getRealm().getName();
      stack = StringEscapeUtils.escapeJson(Io.stackTraceToString(e));
    } catch (Exception ignore) {
    }
    try {
      send(
          "slack-debug.json",
          ImmutableMap.of("env", env(), "realm", realm, "message", message, "details", stack),
          session);
    } catch (Exception se) {
      se.printStackTrace();
    }
  }
}
