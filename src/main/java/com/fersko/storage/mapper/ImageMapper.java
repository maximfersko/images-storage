package com.fersko.storage.mapper;

import com.fersko.storage.dto.ImageInfoDto;
import com.fersko.storage.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Mapper(componentModel = "spring")
public abstract class ImageMapper {

	@Mapping(target = "uploadedTime", source = "uploadedTime")
	@Mapping(target = "url", expression = "java(mapUrl(image.getId()))")
	public abstract ImageInfoDto imageToImageInfoDto(Image image);

	protected String mapUrl(Long id) {
		return ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/storage")
				.path("/image/")
				.path(id.toString())
				.toUriString();
	}
}
