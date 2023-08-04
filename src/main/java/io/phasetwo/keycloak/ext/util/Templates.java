package io.phasetwo.keycloak.ext.util;

import java.util.Map;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.keycloak.util.JsonSerialization;

public class Templates {

  @SuppressWarnings("deprecation")
  public static String template(String templatePath, Map<String, ?> values) throws Exception {
    String template = Io.readFileFromClasspath(templatePath);
    Map<String, Object> flatValues = JsonMapFlattener.flatten(values);
    StrSubstitutor sub = new StrSubstitutor(flatValues);
    return sub.replace(template);
  }

  @SuppressWarnings("unchecked")
  public static Map<String, ?> convertValue(Object value) {
    return JsonSerialization.mapper.convertValue(value, Map.class);
  }
}
