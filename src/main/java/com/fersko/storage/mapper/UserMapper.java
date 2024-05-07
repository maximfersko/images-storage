package com.fersko.storage.mapper;

import com.fersko.storage.dto.UserDto;
import com.fersko.storage.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(target = "username", source = "username")
	@Mapping(target = "role", source = "role")
	@Mapping(target = "active", source = "active")
	@Mapping(target = "firstname", source = "firstname")
	@Mapping(target = "email", source = "email")
	UserDto toDto(User user);

}
