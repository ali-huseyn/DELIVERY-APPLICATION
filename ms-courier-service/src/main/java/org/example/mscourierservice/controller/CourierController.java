package org.example.mscourierservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.mscourierservice.dto.CourierRequestDto;
import org.example.mscourierservice.dto.CourierResponseDto;
import org.example.mscourierservice.enums.CourierStatus;
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
    public List<CourierResponseDto> getCouriers(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        return courierService.getCouriers(page, size);
    }

    @GetMapping("/{id}")
    public CourierResponseDto getCourierById(@PathVariable Long id) {
        return courierService.getCourierById(id);
    }

    @GetMapping("/available")
    public CourierResponseDto getAvailableCourier() {
        return courierService.getAvailableCourier();
    }
    //PATCH /couriers/{id}/status
    @PatchMapping("/{id}/status")
    public CourierResponseDto updateCourierStatus(@PathVariable Long id, @RequestBody CourierStatus status) {
         courierService.updateCourierStatus(id,status);
          return courierService.getCourierById(id);
    }


}

