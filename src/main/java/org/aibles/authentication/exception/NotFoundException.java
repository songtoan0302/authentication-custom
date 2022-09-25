package org.aibles.authentication.exception;

import org.springframework.http.HttpStatus;

/**
 * @author toanns
 */
public class NotFoundException extends BaseException {
  private static final String CODE_NOT_FOUND_EXCEPTION = "org.aibles.authentication.exception.NotFoundException";

  public NotFoundException(String key, String value) {
    setStatus(HttpStatus.NOT_FOUND.value());
    setCode(CODE_NOT_FOUND_EXCEPTION);
    addParam(key, value);
  }
}
