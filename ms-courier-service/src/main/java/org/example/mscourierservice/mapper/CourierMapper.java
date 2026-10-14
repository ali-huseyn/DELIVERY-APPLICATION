package org.example.mscourierservice.mapper;

import org.example.mscourierservice.dto.CourierRequestDto;
import org.example.mscourierservice.dto.CourierResponseDto;
import org.example.mscourierservice.entity.CourierEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourierMapper {

    CourierEntity toEntity(CourierRequestDto courierRequestDto);

    List<CourierResponseDto> toCourierResponseDtoList(List<CourierEntity> courierEntities);

    CourierResponseDto toCourierResponseDto(CourierEntity courierEntity);


}
