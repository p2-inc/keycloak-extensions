> :rocket: **Free** [Keycloak as a service](https://phasetwo.io/?utm_source=github&utm_medium=readme&utm_campaign=keycloak-extesions).

# keycloak-extensions

Common utilities for Keycloak extensions. This is not an extension, but is meant to reduce some boilerplate for extension authors. Used in [Phase Two's](https://phasetwo.io) [Keycloak extensions](https://github.com/p2-inc).

## Using in your extensions

```xml
    <dependency>
      <groupId>io.phasetwo.keycloak</groupId>
      <artifactId>keycloak-extensions</artifactId>
      <version>VERSION</version>
    </dependency>
```
Depending on your mechanism of packaging, you'll either need to include this in an uber-jar (e.g. using the Maven Shade plugin) or put this jar in the `provders/` directory in your Keycloak distribution (in the case where multiple extensions depend on it).

---

All documentation, source code and other files in this repository are Copyright 2023 Phase Two, Inc.

