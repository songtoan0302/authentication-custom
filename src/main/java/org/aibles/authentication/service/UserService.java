package org.aibles.authentication.service;

import org.aibles.authentication.dto.request.UserRequestDTO;
import org.aibles.authentication.dto.request.UserUpdateDTO;
import org.aibles.authentication.entity.User;

public interface UserService {

  /**
   * method crete dùng để đăng kí 1 user mới hay nói cách khác là tạo 1 user bằng email và password
   * sao cho email không bị trùng nếu trùng email đã tồn tại thì ném ra 409 conflict
   *
   * @param userRequestDTO
   */
  void create(UserRequestDTO userRequestDTO);

  /**
   * method update dùng để đổi mật khẩu cho user bằng cách lấy thông tin user bằng email sau đó mã
   * hóa oldPassword đầu vào và so sánh với password được lưu trong db nếu giống nhau thì mã hóa
   * newPassword sau đó thực hiện thao tác setPassword bằng newPassword và lưu vào db nếu không
   * giống thì trả về 401
   *
   * @param userUpdateDTO
   */
  void update(UserUpdateDTO userUpdateDTO);

  /**
   * method get dùng để lấy thông tin của user và thông tin của user profile nếu không tồn tại thì
   * trả về 404
   *
   * @param id
   * @return
   */
  UserRequestDTO get(String id);

  /**
   * method findByEmail dùng để lấy thông tin user bằng email nếu không tồn tại thì trả về 404
   *
   * @param email
   * @return
   */
  User findByEmail(String email);

  /**
   * method login dùng để đăng nhập vào hệ thống bằng email và password, method login sẽ thực hiện
   * việc xác thực email và password .nếu email không tồn tại thì trả về 401 .nếu password giống
   * password trong db thì bắt đầu tạo jwt và sau đó trả về 1 chuỗi jwt, nếu không giống thì trả về
   * 401
   *Mỗi lần đăng nhập ta sẽ có 1 jwt riêng
   * @param userRequestDTO
   * @return
   */

  String login(UserRequestDTO userRequestDTO);

  /**
   * method logout dùng để đăng xuất khỏi hệ thống. bằng việc giải mã jwt, ta có thể lấy được email
   * sau đó lấy thông tin user bằng email. sau dó ta so sánh jwt từ request và jwt trong db
   * nếu bằng thì setJwt bằng null để đảm bảo sau khi logout user không còn truy cập vào được hệ
   * thống bằng jwt cũ nữa
   *
   * @param jwt
   */
  void logout(String jwt);
}
