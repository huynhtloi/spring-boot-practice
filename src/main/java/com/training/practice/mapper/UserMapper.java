package com.training.practice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.training.practice.dto.UserDTO;
import com.training.practice.dto.UserUpdateDTO;
import com.training.practice.dto.UserV2DTO;
import com.training.practice.entity.User;

@Mapper(componentModel = "spring", 
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, // check source object for null values
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) // ignore null values in source object when mapping
public interface UserMapper {
    
    // Entity -> DTO (exclude createdAt, updatedAt - for response purposes)
    UserDTO toDTO(User user);

    UserV2DTO toV2DTO(User user);

    // DTO -> Entity (for create operations)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    User toEntity(UserDTO userDTO);
    
    // Update existing entity from UpdateDTO (for partial update operations)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    void updateEntityFromUpdateDTO(UserUpdateDTO dto, @MappingTarget User entity);
}
