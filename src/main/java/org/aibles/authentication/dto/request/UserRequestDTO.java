package org.aibles.authentication.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDTO {

  @NotBlank(message = "email can't blank!")
  private String email;
  @NotBlank(message = "password can't blank!")
  private String password;

}
