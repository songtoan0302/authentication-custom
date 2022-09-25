package org.aibles.authentication.service.impl;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.aibles.authentication.dto.request.UserRequestDTO;
import org.aibles.authentication.dto.request.UserUpdateDTO;
import org.aibles.authentication.entity.User;
import org.aibles.authentication.exception.ConflictException;
import org.aibles.authentication.exception.NotFoundException;
import org.aibles.authentication.exception.UnauthorizedException;
import org.aibles.authentication.repository.UserRepository;
import org.aibles.authentication.service.UserService;
import org.aibles.authentication.utils.Base64Encoding;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private static final String USER_NOT_FOUND_KEY_EMAIL = "email";
  private static final String USER_NOT_FOUND_KEY_ID = "id";
  private static final String MESSAGE_BAD_REQUEST_OLD_PASSWORD_INVALID = "oldPassword invalid!";
  private static final String STRING_SEPARATOR = " ";
  private static final int EMAIL_INDEX = 0;
  private final UserRepository repository;
  private final ModelMapper modelMapper;

  /**
   * method crete dùng để đăng kí 1 user mới hay nói cách khác là tạo 1 user bằng email và password
   * sao cho email không bị trùng
   * nếu trùng email đã tồn tại thì ném ra 409 conflict
   *
   * @param userRequestDTO
   */
  @Override
  public void create(UserRequestDTO userRequestDTO) {
    if (repository.existsByEmail(userRequestDTO.getEmail())) {
      throw new ConflictException(userRequestDTO.getEmail());
    }
    var userCreate = modelMapper.map(userRequestDTO, User.class);
    userCreate.setPassword(Base64Encoding.encrypt(userCreate.getPassword()));
    repository.save(userCreate);
  }

  @Override
  public void update(UserUpdateDTO userUpdateDTO) {
    var userUpdate = repository.findByEmail(userUpdateDTO.getEmail())
        .map(user -> {
          var oldPassword = user.getPassword();
          if (oldPassword.equals(Base64Encoding.encrypt(userUpdateDTO.getOldPassword()))) {
            user.setPassword(Base64Encoding.encrypt(userUpdateDTO.getNewPassword()));
          } else {
            throw new UnauthorizedException();
          }
          return user;
        })
        .orElseThrow(() -> {
          throw new NotFoundException(USER_NOT_FOUND_KEY_EMAIL, userUpdateDTO.getEmail());
        });
    repository.save(userUpdate);

  }

  @Override
  public UserRequestDTO get(String id) {
    return repository.findById(id)
        .map(user -> modelMapper.map(user, UserRequestDTO.class))
        .orElseThrow(() -> {
          throw new NotFoundException(USER_NOT_FOUND_KEY_ID, id);
        });
  }

  @Override
  public User findByEmail(String email) {
    return repository.findByEmail(email).orElseThrow(() -> {
      throw new NotFoundException(USER_NOT_FOUND_KEY_EMAIL, email);
    });
  }

  @Override
  public String login(UserRequestDTO userRequestDTO) {
    var userLogin = repository.findByEmail(userRequestDTO.getEmail()).orElseThrow(() -> {
      throw new UnauthorizedException();
    });
    var passwordEncrypt = Base64Encoding.encrypt(userRequestDTO.getPassword());
    if (Objects.equals(passwordEncrypt, userLogin.getPassword())) {
      var jwt = generatesJWT(userRequestDTO.getEmail());
      userLogin.setJwt(jwt);
      repository.save(userLogin);
      return jwt;
    } else {
      throw new UnauthorizedException();
    }
  }

  @Override
  public void logout(String jwt) {
    var email = Base64Encoding.decrypt(jwt).split(STRING_SEPARATOR)[EMAIL_INDEX];
    var user = findByEmail(email);
    user.setJwt(null);
    repository.save(user);
  }

  /**
   * method generatesJWT tạo jwt bằng cách tạo 1 chuỗi từ email + dấu cách + 1 chuỗi uuid(được sinh
   * bằng hàm randomUUID()) sau đó mã hóa bằng Base64
   * @param email
   * @return
   */
  private String generatesJWT(String email) {
    var uuid = java.util.UUID.randomUUID().toString();
    return Base64Encoding.encrypt(email + STRING_SEPARATOR + uuid);
  }

}
/**
 * chưa xử lý được vấn đề khi jwt gửi lên bị sai
 */