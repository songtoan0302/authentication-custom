package org.aibles.authentication.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ToanNS
 */
public class BaseException extends RuntimeException {

  private static final long serialVersionUID = -65345345345453L;

  private int status;
  private String code;
  private Map<String, Object> params;


  public void addParam(String key, Object value) {
    if (this.params == null) {
      this.params = new HashMap<>();
    }
    this.params.put(key, value);
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Map<String, Object> getParams() {
    return params;
  }
}
