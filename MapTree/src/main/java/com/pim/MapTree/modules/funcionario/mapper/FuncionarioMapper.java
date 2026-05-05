package com.pim.MapTree.modules.funcionario.mapper;

import com.pim.MapTree.modules.funcionario.dto.FuncionarioDTO;
import com.pim.MapTree.modules.funcionario.entity.Funcionario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Mapper(componentModel = "spring") //caso deixa um parametro em branco sem ser mapeado ele mostra o erro
public abstract class FuncionarioMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    // 2. Usamos o 'expression' para dizer ao MapStruct:
    // "Na hora de preencher o campo password, execute este código Java"
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.password()))")
    public abstract Funcionario toEntity(FuncionarioDTO dto);

    @Mapping(target = "password", ignore = true)
    public abstract FuncionarioDTO toDTO(Funcionario entity);

    // O MapStruct vai implementar o loop e a conversão de cada item automaticamente!
    public abstract List<FuncionarioDTO> toDTOList(List<Funcionario> entity);

    @Mapping(target = "id", ignore = true) // Nunca atualizamos o ID
    @Mapping(target = "password", ignore = true) //senha trata separada
    public abstract void toDTOUpdate(FuncionarioDTO dto, @MappingTarget Funcionario entity);



//    public static Funcionario toEntity(FuncionarioDTO dto, PasswordEncoder passwordEncoder) {
//        var entity = new Funcionario();
//        entity.setName(dto.name());
//        entity.setEmail(dto.email());
//        entity.setCpf(dto.cpf());
//        entity.setPhone(dto.phone());
//        entity.setRole(dto.role());
//
//        entity.setPassword(passwordEncoder.encode(dto.password()));
//        return entity;
//    }
//
//    public static FuncionarioDTO toDTO(Funcionario entity) {
//        return new FuncionarioDTO(
//                entity.getName(),
//                entity.getEmail(),
//                entity.getCpf(),
//                null,
//                entity.getPhone(),
//                entity.getRole()
//        );
//    }
}
