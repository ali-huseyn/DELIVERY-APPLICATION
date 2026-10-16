package org.example.mscourierservice.service;

import lombok.RequiredArgsConstructor;
import org.example.mscourierservice.dto.CourierRequestDto;
import org.example.mscourierservice.dto.CourierResponseDto;
import org.example.mscourierservice.mapper.CourierMapper;
import org.example.mscourierservice.repository.CourierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.mscourierservice.enums.CourierStatus.FREE;

@Service
@RequiredArgsConstructor
public class CourierService {
    private final CourierRepository courierRepository;
    private final CourierMapper courierMapper;

    public void createCourier(CourierRequestDto courierRequestDto) {

        var entity = courierMapper.toEntity(courierRequestDto);
        courierRepository.save(entity);

    }

    public List<CourierResponseDto> getCouriers() {

        var result = courierRepository.findAll();
        return courierMapper.toCourierResponseDtoList(result);

    }

    public CourierResponseDto getCourierById(Long id) {

        var courier = courierRepository.findById(id);

        if (courier.isEmpty()) {
            throw new RuntimeException("Courier not found");
        }
        return courierMapper.toCourierResponseDto(courier.get());

    }

    public CourierResponseDto getAvailableCourier() {

        var result = courierRepository.findFirstByStatus(FREE);

        if (result == null) {
           return null;
        }
        return courierMapper.toCourierResponseDto(result);

    }


}
