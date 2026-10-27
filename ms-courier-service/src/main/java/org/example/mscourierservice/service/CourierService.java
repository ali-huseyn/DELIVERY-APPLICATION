package org.example.mscourierservice.service;

import lombok.RequiredArgsConstructor;
import org.example.mscourierservice.dto.CourierRequestDto;
import org.example.mscourierservice.dto.CourierResponseDto;
import org.example.mscourierservice.entity.CourierEntity;
import org.example.mscourierservice.enums.CourierStatus;
import org.example.mscourierservice.exception.CourierNotFound;
import org.example.mscourierservice.exception.NoAvailableCourierException;
import org.example.mscourierservice.mapper.CourierMapper;
import org.example.mscourierservice.repository.CourierRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public List<CourierResponseDto> getCouriers(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<CourierEntity> couriers = courierRepository.findAll(pageable);


        return courierMapper.toCourierResponseDtoList(couriers.getContent());

    }

    public CourierEntity fetchCourierById(Long id) {
        return courierRepository.findById(id).orElseThrow(() -> new CourierNotFound("Courier not found"));
    }


    public CourierResponseDto getCourierById(Long id) {

        var courier = fetchCourierById(id);
        return courierMapper.toCourierResponseDto(courier);

    }

    public CourierResponseDto getAvailableCourier() {

        var result = courierRepository.findFirstByStatus(FREE)
                .orElseThrow(() -> new NoAvailableCourierException("No available courier found"));


        return courierMapper.toCourierResponseDto(result);

    }
    public void updateCourierStatus(Long id, CourierStatus status) {
        var courier = fetchCourierById(id);
        courier.setStatus(status);
        courierRepository.save(courier);
    }


}
