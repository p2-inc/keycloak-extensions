package io.phasetwo.keycloak.ext.util;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.Map;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.keycloak.broker.provider.util.SimpleHttp;

/** Helper for collecting usage stats */
public class Stats {

  public static final String PHASETWO_ANALYTICS_DISABLED_KEY = "PHASETWO_ANALYTICS_DISABLED";
  // todo make a redirect
  public static final String PHASETWO_ANALYTICS_URL = "https://eooemvvcxy5k16a.m.pipedream.net";

  public static void collect(String name, String version, String commit, Object... args) {
    MultivaluedMap<String, Object> info = new MultivaluedHashMap<String, Object>();
    info.add("name", name);
    info.add("version", version);
    info.add("commit", commit);
    if (args != null) info.addAll("args", args);
    collect(info);
  }

  public static void collect(MultivaluedMap<String, Object> info) {
    String enabled = System.getenv(PHASETWO_ANALYTICS_DISABLED_KEY);
    if (Boolean.valueOf(enabled)) {
      return; // do nothing if anaylitics is disabled
    }

    UriBuilder u = UriBuilder.fromUri(PHASETWO_ANALYTICS_URL);
    for (Map.Entry<String, List<Object>> e : info.entrySet()) {
      u.queryParam(e.getKey(), e.getValue().toArray());
    }

    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      SimpleHttp.doPost(u.build().toString(), httpClient).json(info).asResponse();
    } catch (Exception ignore) {
    }
  }
}
