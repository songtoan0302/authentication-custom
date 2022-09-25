package org.aibles.authentication.entity;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {

  @Id
  @Column(name = "id", unique = true, nullable = false)
  private String id;
  @Column(name = "email", unique = true, nullable = false)
  @NotBlank
  private String email;
  @Column(name = "password", nullable = false)
  @NotBlank
  private String password;
  @Column(name = "jwt", unique = true)
  private String jwt;
  @OneToOne(mappedBy = "user")
  public UserProfile userProfile;

  @PrePersist
  public void prePersistId() {
    this.id = this.id == null ? UUID.randomUUID().toString() : id;
  }
}
