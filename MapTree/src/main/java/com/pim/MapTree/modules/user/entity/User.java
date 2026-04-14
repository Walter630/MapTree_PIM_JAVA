package com.pim.MapTree.modules.user.entity;

import com.pim.MapTree.modules.user.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tb_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;
    private String password;

    private Roles role;

    public User(UserDTO userDTO) {
        this.name = userDTO.getName();
        this.password = userDTO.getPassword();
        this.role = getRole();
    }

    private static User toEntity(UserDTO userDTO) {
        return User.builder()
                .name(userDTO.getName())
                .password(userDTO.getPassword())
                .build();
    }
}
