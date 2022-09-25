package org.aibles.authentication.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnauthorizedException extends BaseException{
  private static final String UNAUTHORIZED_KEY = "User";
  private static final String UNAUTHORIZED_MESSAGE = "email or password invalid!";
  private static final String CODE_UNAUTHORIZED_EXCEPTION = "org.aibles.authentication.exception.UnauthorizedException";

  public UnauthorizedException() {
    setStatus(HttpStatus.UNAUTHORIZED.value());
    setCode(CODE_UNAUTHORIZED_EXCEPTION);
    addParam(UNAUTHORIZED_KEY, UNAUTHORIZED_MESSAGE);
  }
}
