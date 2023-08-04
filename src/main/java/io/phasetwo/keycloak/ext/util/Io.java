package io.phasetwo.keycloak.ext.util;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.keycloak.common.ClientConnection;
import org.keycloak.http.HttpRequest;

public class Io {

  public static Path createTempDir() {
    try {
      return Files.createTempDirectory(null);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  public static void deleteDir(Path base) throws IOException {
    Files.walk(base).map(Path::toFile).sorted((o1, o2) -> -o1.compareTo(o2)).forEach(File::delete);
  }

  public static boolean notEmpty(String... strs) {
    for (String s : strs) {
      if (Strings.isNullOrEmpty(s)) {
        return false;
      }
    }
    return true;
  }

  public static boolean empty(String... strs) {
    for (String s : strs) {
      if (!Strings.isNullOrEmpty(s)) {
        return false;
      }
    }
    return true;
  }

  public static String urlencode(String s) {
    try {
      return URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
    } catch (UnsupportedEncodingException ignore) {
    }
    return null;
  }

  public static URI toUri(String u) {
    try {
      return new URI(u);
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static String getIp(HttpRequest request, ClientConnection connection) {
    String ip = null;
    List<String> ips = request.getHttpHeaders().getRequestHeader("X-Forwarded-For");
    if (ips != null && ips.size() > 0) ip = ips.get(0);
    if (ip == null || "".equals(ip)) {
      ip = connection.getRemoteAddr();
    }
    return ip;
  }

  public static String mapToString(Map m) {
    return Joiner.on("\n").withKeyValueSeparator(" => ").join(m);
  }

  public static String readFileFromClasspath(String path) throws IOException {
    try (InputStream is = Io.class.getClassLoader().getResourceAsStream(path)) {
      return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
          .lines()
          .collect(Collectors.joining("\n"));
    }
  }

  public static String stackTraceToString(Throwable e) {
    if (e == null) return "";
    else return ExceptionUtils.getStackTrace(e);
  }

  public static void lowercaseAlphaOnly(String input) {
    if (!input.matches("^[a-z0-9][a-z0-9\\-]*[a-z0-9]$")) { // regex by chatgpt
      throw new IllegalArgumentException(
          "Input string must be lower case, alphanumeric. Hyphen is the only allowed special character.");
    }
  }
}
