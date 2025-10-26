package org.example.termproject_bouffard.DTO;

import lombok.*;
// Noah Bouffard : 2431848

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
