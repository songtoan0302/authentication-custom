package org.aibles.authentication.exception;


import org.springframework.http.HttpStatus;

/**
 * @author toanns
 */
public class BadRequestException extends BaseException {

  private static final String CODE_BAD_REQUEST = "org.aibles.authentication.exception.BadRequestException";
  private static final String KEY_PARAM = "data invalid";

  public BadRequestException(Object dataInput) {
    setCode(CODE_BAD_REQUEST);
    setStatus(HttpStatus.BAD_REQUEST.value());
    addParam(KEY_PARAM, dataInput);
  }
}
