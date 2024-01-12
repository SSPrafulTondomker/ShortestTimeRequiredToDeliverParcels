package com.example.lucidity.dto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NodeAndWeightPair implements Comparable<NodeAndWeightPair>{
    String id;
    int distance;


    @Override
    public int compareTo(NodeAndWeightPair o) {
        return this.distance - o.getDistance();
    }
}
