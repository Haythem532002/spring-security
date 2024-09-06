package com.haythem.Security.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.haythem.Security.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id
    @GeneratedValue
    Integer id;
    @Column(unique = true)
    String name;

//    @ManyToMany(mappedBy = "roles")
//    @JsonIgnore
//    List<User> users;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false)
    LocalDateTime lastModifiedDate;

}
