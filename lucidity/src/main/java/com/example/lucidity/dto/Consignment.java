package com.example.lucidity.dto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Consignment {
    User sender;
    Restaurant restaurant;
    User receiver;
    String Id;
}
