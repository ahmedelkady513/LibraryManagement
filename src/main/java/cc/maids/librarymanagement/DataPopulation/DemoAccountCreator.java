package cc.maids.librarymanagement.DataPopulation;

import cc.maids.librarymanagement.user.entity.AdminUser;
import cc.maids.librarymanagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DemoAccountCreator {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void createDemoAccount() {
        if (userRepository.count() == 0) {
            AdminUser adminUser = new AdminUser();
            adminUser.setUsername("admin");
            adminUser.setPassword(bCryptPasswordEncoder.encode("admin"));
            userRepository.save(adminUser);
        }
    }
    
}
