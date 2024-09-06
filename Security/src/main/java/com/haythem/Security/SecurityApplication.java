package com.haythem.Security;

import com.haythem.Security.auth.AuthenticationService;
import com.haythem.Security.auth.RegistrationRequest;
import com.haythem.Security.role.Role;
import com.haythem.Security.role.RoleRepository;
import com.haythem.Security.user.RoleEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

//	@Bean
//	public CommandLineRunner runner(RoleRepository roleRepository) {
//		return args -> {
//			if(roleRepository.findByName("USER").isEmpty()) {
//				roleRepository.save(
//						Role.builder().name("USER").build()
//				);
//			}
//		};
//	}

//    @Bean
//    public CommandLineRunner runner(AuthenticationService service) {
//        return args -> {
//            var admin = RegistrationRequest.builder()
//                    .firstName("ADMIN")
//                    .lastName("ADMIN")
//                    .email("admin@mail.com")
//                    .password("password")
//                    .role(RoleEnum.ADMIN)
//                    .build();
//            service.register(admin);
////            System.out.println("ADMIN token is generated");
//
//            var manager = RegistrationRequest.builder()
//                    .firstName("Admin")
//                    .lastName("Admin")
//                    .email("manager@mail.com")
//                    .password("password")
//                    .role(RoleEnum.MANAGER)
//                    .build();
//            service.register(manager);
////            System.out.println("Manager token is generated");
//        };
//    }

}
