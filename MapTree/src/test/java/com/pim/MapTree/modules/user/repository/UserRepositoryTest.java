package com.pim.MapTree.modules.user.repository;

import com.pim.MapTree.modules.user.dto.UserDTO;
import com.pim.MapTree.modules.user.entity.Roles;
import com.pim.MapTree.modules.user.entity.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should return User successful from DB")
    void findByNameSuccess() {
        UserDTO user = new UserDTO("walter", "admin123", Roles.ADMIN);
        this.createUser(user);

        Optional<User> foundedUser = this.userRepository.findByName("walter");
        assertTrue(foundedUser.isPresent());
    }

    private User createUser(UserDTO userDTO) {
        User user = new User(userDTO);
        this.entityManager.persist(user);
        return user;
    }
}