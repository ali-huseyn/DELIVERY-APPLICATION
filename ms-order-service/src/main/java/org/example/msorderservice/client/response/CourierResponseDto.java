package org.example.msorderservice.client.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourierResponseDto {
    private Long id;
    private String name;
    private String email;
    private String status;

}
