package org.aibles.authentication.dto.reponse;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserResponseDTO {
  @NotBlank(message = "email can't blank!")
  private String email;
  @NotBlank(message = "password can't blank!")
  private String password;
}
