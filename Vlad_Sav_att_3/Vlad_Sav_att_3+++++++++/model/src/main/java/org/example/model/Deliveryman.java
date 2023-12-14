package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deliveryman {
    private Integer id;
    private String fio;
    private Integer deliveryServiceId;
    private Integer orderId;

}
