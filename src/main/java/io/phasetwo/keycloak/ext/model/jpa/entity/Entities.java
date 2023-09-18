package io.phasetwo.keycloak.ext.model.jpa.entity;

import java.util.Collection;

public class Entities {

  public static <T> void setCollection(Collection<T> src, Collection<T> dest) {
    if (dest == null) {
      dest = src;
    } else if (dest != src) {
      dest.clear();
      if (src != null) {
        dest.addAll(src);
      }
    }
  }
}
