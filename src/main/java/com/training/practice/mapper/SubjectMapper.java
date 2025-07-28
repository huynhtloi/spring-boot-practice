package com.training.practice.mapper;

import com.training.practice.dto.SubjectUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.training.practice.dto.SubjectDTO;
import com.training.practice.dto.SubjectCreateDTO;
import com.training.practice.entity.Subject;

@Mapper(componentModel = "spring", 
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SubjectMapper {
    // Entity -> DTO (for response purposes) - timestamps already excluded from DTO
    SubjectDTO toDTO(Subject subject);
    
    // DTO -> Entity (for create operations)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    Subject toEntity(SubjectDTO dto);
    
    // CreateDTO -> Entity (for create operations from CreateDTO)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    Subject createDTOToEntity(SubjectCreateDTO dto);

    // Update existing entity from UpdateDTO (for partial update operations)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntityFromUpdateDTO(SubjectUpdateDTO dto, @MappingTarget Subject entity);
}
