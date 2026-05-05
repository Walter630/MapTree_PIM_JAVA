package com.pim.MapTree.modules.empresa.mapper;

import com.pim.MapTree.modules.empresa.dto.EmpresaDTO;
import com.pim.MapTree.modules.empresa.entity.Empresa;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class EmpresaMapper {
    public abstract Empresa toEntity(EmpresaDTO empresa);
    public abstract EmpresaDTO toDTO(Empresa empresa);
    public abstract List<EmpresaDTO> toDTOList(List<Empresa> empresas);
}
