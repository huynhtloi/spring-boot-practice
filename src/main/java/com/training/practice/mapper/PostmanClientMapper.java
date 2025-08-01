package com.training.practice.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.training.practice.dto.backend.PostmanResponse;
import com.training.practice.dto.external.PostmanClientResponse;

@Mapper(componentModel = "spring", 
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostmanClientMapper {
    @Mapping(target = "name", source = "userPostmanName")
    @Mapping(target = "email", source = "userPostmanEmail")
    @Mapping(target = "status", source = "userPostmanStatus")
    @Mapping(target = "roles", source = "userPostmanRoles")
    PostmanClientResponse toClientExternal(PostmanResponse response);

    @Mapping(target = "userPostmanName", source = "name")
    @Mapping(target = "userPostmanEmail", source = "email")
    @Mapping(target = "userPostmanStatus", source = "status")
    @Mapping(target = "userPostmanRoles", source = "roles")
    PostmanResponse toBeInternal(PostmanClientResponse response);

    @Mapping(target = "userPostmanName", source = "name")
    @Mapping(target = "userPostmanEmail", source = "email")
    @Mapping(target = "userPostmanStatus", source = "status")
    @Mapping(target = "userPostmanRoles", source = "roles")
    List<PostmanResponse> toBeInternals(List<PostmanClientResponse> response);
}
