package io.phasetwo.keycloak.ext.util;


import java.util.HashMap;
import java.util.Map;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.keycloak.broker.provider.util.SimpleHttp;

/** Helper for collecting usage stats */
public class Stats {

  public static final String PHASETWO_ANALYTICS_DISABLED_KEY = "PHASETWO_ANALYTICS_DISABLED";
  public static final String PHASETWO_ANALYTICS_URL = "https://eooemvvcxy5k16a.m.pipedream.net";

  public static void collect(String name, String version, String commit, Object... args) {
    Map<String, Object> info = new HashMap<String, Object>();
    info.put("name", name);
    info.put("version", version);
    info.put("commit", commit);
    if (args != null) info.put("args", args);
    collect(info);
  }

  public static void collect(Map<String, Object> info) {
    String enabled = System.getenv(PHASETWO_ANALYTICS_DISABLED_KEY);
    if (Boolean.valueOf(enabled)) {
      return; // do nothing if anaylitics is disabled
    }

    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      SimpleHttp.Response response =
          SimpleHttp.doPost(PHASETWO_ANALYTICS_URL, httpClient).json(info).asResponse();
    } catch (Exception ignore) {
    }
  }
}
