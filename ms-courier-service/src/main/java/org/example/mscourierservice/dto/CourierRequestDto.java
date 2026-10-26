package org.example.mscourierservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourierRequestDto {
    private String name;
    private String email;
    private String phoneNumber;
}
