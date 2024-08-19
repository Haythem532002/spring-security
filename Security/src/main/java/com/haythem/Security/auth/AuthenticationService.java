package com.haythem.Security.auth;

import com.haythem.Security.email.EmailService;
import com.haythem.Security.email.EmailTemplateName;
import com.haythem.Security.role.RoleRepository;
import com.haythem.Security.user.Token;
import com.haythem.Security.user.TokenRepository;
import com.haythem.Security.user.User;
import com.haythem.Security.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;
    public void register(RegistrationRequest request) throws MessagingException {
        var userRole = roleRepository.findByName("USER")
                //todo - better exception handling
                .orElseThrow(()->new IllegalStateException("ROLE USER was not initialized"));
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build()
                ;
        userRepository.save(user);
        sendvalidationEmail(user);
    }

    private void sendvalidationEmail(User user) throws MessagingException {
        var newToken = generateAndSavaActivationToken(user);
        //send email

        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account Activation"
        );
    }

    private String generateAndSavaActivationToken(User user) {
        //generate token
        String generatedToken = generateAndSavaActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build()
                ;
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateAndSavaActivationCode(int length) {
        String charachters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for(int i=0;i<length;i++) {
            int randomIndex = secureRandom.nextInt(charachters.length());
            codeBuilder.append(charachters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}
