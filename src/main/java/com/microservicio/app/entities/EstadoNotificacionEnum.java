package com.microservicio.app.entities;

import java.util.Arrays;
import java.util.Objects;

import org.bouncycastle.util.Strings;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoNotificacionEnum {

  APROBADA_LEIDA(0, "APROBADA LEIDA"),
  NO_APROBADA_LEIDA(1, "NO APROBADA LEIDA"),
  PENDIENTE_LEIDA(2, "PENDIENTE LEIDA"),
  APROBADA_NO_LEIDA(3, "APROBADA NO LEIDA"),
  NO_APROBADA_NO_LEIDA(4, "NO APROBADA NO LEIDA"),
  PENDIENTE_NO_LEIDA(5, "PENDIENTE NO LEIDA");
  
  private final Integer id;
  
  private final String name;

  public static EstadoNotificacionEnum of(Integer id) {
    return Arrays.stream(values())
      .filter(item -> item.getId().equals(id))
      .findFirst()
      .orElse(null);
  }

  public static EstadoNotificacionEnum of(String name) {
    return Arrays.stream(values())
      .filter(Objects::nonNull)
      .filter(item -> item.getName().equals(Strings.toUpperCase(name)))
      .findFirst()
      .orElse(null);
  }
}
