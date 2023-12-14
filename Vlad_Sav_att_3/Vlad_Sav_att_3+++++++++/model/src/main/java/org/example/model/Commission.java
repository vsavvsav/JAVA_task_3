package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commission {
    private Integer id;
    private Integer cost;
    private Deliveryman deliveryman;
    private Integer deliveryServiceId;
}
