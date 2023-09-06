package io.phasetwo.keycloak.ext.util;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.HashMap;
import java.util.Map;
import org.keycloak.util.JsonSerialization;

/** Helper for building a Map for use with ServerInfoAwareProviderFactory */
public class ServerInfoBuilder {

  private final Map<String, String> map;

  ServerInfoBuilder() {
    this.map = new HashMap<String, String>();
  }

  static ServerInfoBuilder builder() {
    return new ServerInfoBuilder();
  }

  public ServerInfoBuilder add(String key, Object value) {
    this.map.put(key, value.toString());
    return this;
  }

  public ServerInfoBuilder addObject(Object obj) {
    this.map.putAll(JsonSerialization.mapper.convertValue(obj, new TypeReference<>() {}));
    return this;
  }

  public Map<String, String> build() {
    return this.map;
  }
}
