package com.example.lucidity.dto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    String name;
    Location position;
    String Id;
}
