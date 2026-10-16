package org.example.mscourierservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.mscourierservice.dto.CourierRequestDto;
import org.example.mscourierservice.dto.CourierResponseDto;
import org.example.mscourierservice.service.CourierService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/couriers")
public class CourierController {

    private final CourierService courierService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCourier(@RequestBody CourierRequestDto courierRequestDto) {
        courierService.createCourier(courierRequestDto);
    }

    @GetMapping
    public List<CourierResponseDto> getCouriers() {
        return courierService.getCouriers();
    }

    @GetMapping("/{id}")
    public CourierResponseDto getCourierById(@PathVariable Long id) {
        return courierService.getCourierById(id);
    }

    @GetMapping("/available")
    public CourierResponseDto getAvailableCourier() {
        return courierService.getAvailableCourier();
    }
}
