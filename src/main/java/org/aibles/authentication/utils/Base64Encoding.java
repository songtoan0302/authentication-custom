package org.aibles.authentication.utils;

import java.util.Base64;

/**
 * dùng bộ mã hóa BASE64
 */
public class Base64Encoding {

  /**
   * @param str là 1 chuỗi cần được mã hóa
   * @return 1 chuỗi string đã được mã hóa
   */
  public static String encrypt(String str) {
    return Base64.getEncoder().encodeToString(str.getBytes());
  }

  /**
   * @param str là 1 chuỗi string tồn tại ở dạng đã được mã hóa với BASE64
   * @return 1 chuỗi string đã được giải mã
   */
  public static String decrypt(String str) {
    byte[] decodedBytes = Base64.getDecoder().decode(str);
    return new String(decodedBytes);
  }

}
