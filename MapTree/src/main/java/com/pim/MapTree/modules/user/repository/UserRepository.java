package com.pim.MapTree.modules.user.repository;

import com.pim.MapTree.modules.funcionario.entity.Funcionario;
import com.pim.MapTree.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByName(String name);
}
