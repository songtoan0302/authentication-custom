package org.aibles.authentication.controller;

import lombok.RequiredArgsConstructor;
import org.aibles.authentication.dto.request.UserRequestDTO;
import org.aibles.authentication.dto.request.UserUpdateDTO;
import org.aibles.authentication.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@RequestBody @Validated UserRequestDTO userRequestDTO) {
    service.create(userRequestDTO);
  }


  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public String login(@RequestBody @Validated UserRequestDTO userRequestDTO) {
    return service.login(userRequestDTO);
  }

  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.OK)
  public void logout(@RequestHeader("Bearer") String jwt) {
    service.logout(jwt);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@RequestBody @Validated UserUpdateDTO userUpdateDTO) {
    service.update(userUpdateDTO);
  }

  @GetMapping("/hello")
  @ResponseStatus(HttpStatus.OK)
  public String greeting() {
    return"Hello";
  }
}
