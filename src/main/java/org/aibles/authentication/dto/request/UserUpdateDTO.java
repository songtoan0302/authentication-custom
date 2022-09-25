package org.aibles.authentication.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateDTO {
  @NotBlank(message = "email can't blank!")
  private String email;
  @NotBlank(message = "oldPassword can't blank!")
  private String oldPassword;
  @NotBlank(message = "newPassword can't blank!")
  private String newPassword;
}
