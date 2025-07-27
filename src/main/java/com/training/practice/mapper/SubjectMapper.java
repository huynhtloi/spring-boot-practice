package com.training.practice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import com.training.practice.dto.SubjectDTO;
import com.training.practice.dto.SubjectCreateDTO;
import com.training.practice.entity.Subject;

@Mapper(componentModel = "spring", 
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface SubjectMapper {
    // Entity -> DTO (for response purposes) - include timestamps
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
}
