package org.example.mscourierservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourierResponseDto {
    private String id;
    private String name;
    private String status;

}
