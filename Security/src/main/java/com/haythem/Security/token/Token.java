package com.haythem.Security.token;

import com.haythem.Security.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token {
    @Id
    @GeneratedValue
    Integer id;
    @Column(length = 1024)
    String token;

    @Enumerated(EnumType.STRING)
    TokenType tokenType;
    boolean expired;
    boolean revoked;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

}
