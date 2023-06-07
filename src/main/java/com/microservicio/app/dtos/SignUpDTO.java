package com.microservicio.app.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpDTO {

  private String username;

  private String name;

  private Boolean enabled;

  private String role;

  private String password;

  private String passwordVerification;

}
