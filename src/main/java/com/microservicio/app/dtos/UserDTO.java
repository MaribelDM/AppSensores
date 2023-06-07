package com.microservicio.app.dtos;

import com.microservicio.app.entities.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

  private Long id;
  private RoleEnum role;
  private String username;
  private String name;
  private String surname;

}
