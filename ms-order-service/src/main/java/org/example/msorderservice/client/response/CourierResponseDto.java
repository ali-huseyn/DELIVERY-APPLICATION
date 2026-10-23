package org.example.msorderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourierResponseDto {
    private Long id;
    private String name;
    private String status;

}
