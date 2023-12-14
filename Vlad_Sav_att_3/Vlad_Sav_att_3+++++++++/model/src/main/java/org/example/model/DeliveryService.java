package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryService {
    private Integer id;
    private String name;
    private Collection<Commission> commission;
    private Collection<Warehouse> warehouses;


}
