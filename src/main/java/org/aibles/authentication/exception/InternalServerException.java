package org.aibles.authentication.exception;

import org.springframework.http.HttpStatus;

/**
 * @author toanns
 */
public class InternalServerException extends BaseException {
  private static final String CODE_INTERNAL_SEVER_ERROR = "org.aibles.authentication.exception.InternalServerException";
  private static final String KEY_PARAM = "error";
  public InternalServerException(String error) {
    setCode(CODE_INTERNAL_SEVER_ERROR);
    setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    addParam(KEY_PARAM, error);
  }
}
