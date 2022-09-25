package org.aibles.authentication.entity;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_profile")
public class UserProfile {

  @Id
  @Column(name = "id", unique = true, nullable = false)
  private String id;
  @Column(name = "user_id", unique = true, nullable = false)
  private String userId;
  @JoinColumn(name = "user_id", updatable = false, insertable = false)
  @OneToOne(fetch = FetchType.LAZY)
  public User user;
  @Column(name = "full_name", nullable = false)
  private String fullName;
  @Column(name = "address")
  private String address;
  @Column(name = "gender")
  private String gender;
  @Column(name = "date_of_birth")
  private Instant dateOfBirth;

  @PrePersist
  public void prePersist() {
    this.id = this.id == null ? UUID.randomUUID().toString() : id;
  }
}
