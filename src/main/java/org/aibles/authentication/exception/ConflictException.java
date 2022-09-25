package org.aibles.authentication.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ConflictException extends BaseException {

  private static final String KEY_PARAM_CONFLICT_EXCEPTION = "User";
  private static final String CODE_CONFLICT_EXCEPTION = "org.aibles.authentication.exception.ConflictException";
  public ConflictException(Object param) {
    setStatus(HttpStatus.CONFLICT.value());
    setCode(CODE_CONFLICT_EXCEPTION);
    addParam(KEY_PARAM_CONFLICT_EXCEPTION, param);
  }
}
