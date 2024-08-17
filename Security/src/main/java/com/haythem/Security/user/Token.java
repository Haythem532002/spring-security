package com.haythem.Security.user;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token {
    @Id
    @GeneratedValue
    Integer id;
    String token;
    LocalDateTime createdAt;
    LocalDateTime expiresAt;
    LocalDateTime validatedAt;

    @ManyToOne
    @JoinColumn(name = "UserId",nullable = false)
    User user;

}
