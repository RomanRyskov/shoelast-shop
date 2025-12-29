package romanryskov.shoelastshop.service;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import romanryskov.shoelastshop.model.entity.User;
import romanryskov.shoelastshop.model.enums.UserRole;
import romanryskov.shoelastshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole(UserRole.USER); // по умолчанию пользователь
        }
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found") {
        });
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User updateUser(Long id, User updateUser) {
        User user = getUserById(id);
        user.setUserName(updateUser.getUserName());
        user.setPassword(updateUser.getPassword());
        user.setEmail(updateUser.getEmail());
        user.setRole(updateUser.getRole());
        user.setOrder(updateUser.getOrder());
        return userRepository.save(user);
    }
    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }
        return passwordEncoder.matches(password, user.getPassword());
    }

}