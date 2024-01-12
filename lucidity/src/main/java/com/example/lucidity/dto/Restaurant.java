package com.example.lucidity.dto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    String name;
    Location position;
    String Id;
}
