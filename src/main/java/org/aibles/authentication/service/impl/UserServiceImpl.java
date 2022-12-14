package org.aibles.authentication.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private static final String USER_NOT_FOUND_KEY_EMAIL = "email";
  private static final String USER_NOT_FOUND_KEY_ID = "id";
  private static final String STRING_SEPARATOR = " ";
  private static final int EMAIL_INDEX = 0;
  private final UserRepository repository;
  private final ModelMapper modelMapper;

  /**
   * method crete dùng để đăng kí 1 user mới hay nói cách khác là tạo 1 user bằng email và password
   * sao cho email không bị trùng nếu trùng email đã tồn tại thì ném ra 409 conflict
   *
   * @param userRequestDTO
   */
  @Override
  @Transactional
  public void create(UserRequestDTO userRequestDTO) {
    log.info("(create)userRequestDTO: {}", userRequestDTO);
    if (repository.existsByEmail(userRequestDTO.getEmail())) {
      throw new ConflictException(userRequestDTO.getEmail());
    }
    var userCreate = modelMapper.map(userRequestDTO, User.class);
    userCreate.setPassword(Base64Encoding.encrypt(userCreate.getPassword()));
    repository.save(userCreate);
  }

  @Override
  @Transactional
  public void update(UserUpdateDTO userUpdateDTO) {
    log.info("(update)userUpdateDTO: {}", userUpdateDTO);
    repository.findByEmail(userUpdateDTO.getEmail())
        .map(user -> updatePassword(userUpdateDTO, user))
        .orElseThrow(() -> {
          throw new NotFoundException(USER_NOT_FOUND_KEY_EMAIL, userUpdateDTO.getEmail());
        });

  }

  @Override
  @Transactional(readOnly = true)
  public UserRequestDTO get(String id) {
    log.info("(get)id: {}", id);
    return repository.findById(id)
        .map(user -> modelMapper.map(user, UserRequestDTO.class))
        .orElseThrow(() -> {
          throw new NotFoundException(USER_NOT_FOUND_KEY_ID, id);
        });
  }

  @Override
  @Transactional(readOnly = true)
  public User findByEmail(String email) {
    log.info("(findByEmail)email: {}", email);
    return repository.findByEmail(email)
        .orElseThrow(() -> {
          throw new NotFoundException(USER_NOT_FOUND_KEY_EMAIL, email);
        });
  }

  @Override
  @Transactional
  public String login(UserRequestDTO userRequestDTO) {
    log.info("(login)userRequestDTO: {}", userRequestDTO);
    var passwordEncrypt = Base64Encoding.encrypt(userRequestDTO.getPassword());
    var userLogin = repository.findByEmailAndPassword(userRequestDTO.getEmail(), passwordEncrypt)
        .orElseThrow(() -> {
          throw new UnauthorizedException();
        });

    var jwt = generatesJWT(userRequestDTO.getEmail());
    userLogin.setJwt(jwt);
    repository.save(userLogin);
    return jwt;
  }

  @Override
  @Transactional
  public void logout(String jwt) {
    log.info("(logout)jwt: {}", jwt);
    var user = repository.findByJwt(jwt)
        .orElseThrow(() -> {
          throw new UnauthorizedException();
        });
    user.setJwt(null);
    repository.save(user);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByJwt(String jwt) {
    log.info("(existsByJwt)jwt: {}", jwt);
    return repository.existsByJwt(jwt);
  }

  /**
   * method generatesJWT tạo jwt bằng cách tạo 1 chuỗi từ email + dấu cách + 1 chuỗi uuid(được sinh
   * bằng hàm randomUUID()) sau đó mã hóa bằng Base64
   *
   * @param email
   * @return
   */
  private String generatesJWT(String email) {
    var uuid = java.util.UUID.randomUUID().toString();
    return Base64Encoding.encrypt(email + STRING_SEPARATOR + uuid);
  }

  private User updatePassword(UserUpdateDTO userUpdateDTO, User user) {
    var oldPassword = user.getPassword();

    if (!oldPassword.equals(Base64Encoding.encrypt(userUpdateDTO.getOldPassword()))) {
      throw new UnauthorizedException();
    }

    user.setPassword(Base64Encoding.encrypt(userUpdateDTO.getNewPassword()));
    return repository.save(user);
  }
}