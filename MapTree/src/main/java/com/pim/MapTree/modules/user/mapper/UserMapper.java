package com.pim.MapTree.modules.user.mapper;

import com.pim.MapTree.modules.user.dto.UserDTO;
import com.pim.MapTree.modules.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
    public abstract User toEntity(UserDTO dto);

    @Mapping(target = "password", ignore = true)
    public abstract UserDTO toDTO(User entity);

    public abstract List<UserDTO> toDTOList(List<User> entity);

}
