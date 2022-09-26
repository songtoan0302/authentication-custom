package org.aibles.authentication.exception;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UnauthorizedException extends BaseException {

  private static final String UNAUTHORIZED_KEY = "User";
  private static final String UNAUTHORIZED_MESSAGE = "email or password invalid!";
  private static final String CODE_UNAUTHORIZED_EXCEPTION = "org.aibles.authentication.exception.UnauthorizedException";

  private String key;
  private String value;

  public UnauthorizedException() {
    setStatus(HttpStatus.UNAUTHORIZED.value());
    setCode(CODE_UNAUTHORIZED_EXCEPTION);
    addParam(UNAUTHORIZED_KEY, UNAUTHORIZED_MESSAGE);
  }

  public UnauthorizedException(String key, String value) {
    this.key = StringUtils.isNoneBlank(key) ? key : UNAUTHORIZED_KEY;
    this.value = StringUtils.isNoneBlank(value) ? value : UNAUTHORIZED_MESSAGE;
    setStatus(HttpStatus.UNAUTHORIZED.value());
    setCode(CODE_UNAUTHORIZED_EXCEPTION);
    addParam(key, value);
  }
}
