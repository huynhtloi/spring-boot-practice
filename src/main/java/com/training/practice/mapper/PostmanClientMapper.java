package com.training.practice.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.training.practice.dto.backend.PostmanResponse;
import com.training.practice.dto.external.PostmanClientResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Mapper(componentModel = "spring", 
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class PostmanClientMapper {
    @Mapping(target = "name", source = "userPostmanName")
    @Mapping(target = "email", source = "userPostmanEmail")
    @Mapping(target = "status", source = "userPostmanStatus")
    @Mapping(target = "roles", source = "userPostmanRoles")
    public abstract PostmanClientResponse toClientExternal(PostmanResponse response);

    @Mapping(target = "userPostmanName", source = "name")
    @Mapping(target = "userPostmanEmail", source = "email")
    @Mapping(target = "userPostmanStatus", source = "status")
    @Mapping(target = "userPostmanRoles", source = "roles")
    public abstract PostmanResponse toBeInternal(PostmanClientResponse response);

    @Mapping(target = "userPostmanName", source = "name")
    @Mapping(target = "userPostmanEmail", source = "email")
    @Mapping(target = "userPostmanStatus", source = "status")
    @Mapping(target = "userPostmanRoles", source = "roles")
    public abstract List<PostmanResponse> toBeInternals(List<PostmanClientResponse> response);

    @AfterMapping
    protected void mapUserPostmanStatus(@MappingTarget PostmanResponse internal) {
        if (internal.getUserPostmanStatus() != null && !internal.getUserPostmanStatus().trim().isEmpty()) {
            internal.setUserPostmanStatus("User with " + internal.getUserPostmanStatus().toUpperCase());
        }
    }
}
