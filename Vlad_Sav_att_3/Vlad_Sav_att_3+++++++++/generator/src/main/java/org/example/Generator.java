package org.example;


import org.example.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {
    private Integer count;

    public Generator(Integer count) {
        this.count = count;
    }

    public List<DeliveryService> generate(){
        List<DeliveryService> deliveryServices = new ArrayList<>();
        for (int i = 1; i < count+1; i++) {
            DeliveryService ds = new DeliveryService();
            ds.setId(i);
            ds.setName("DeliveryService_"+i);
            List<Commission> orders = new ArrayList<>();
            for (int j = 1; j < 10; j++) {
                Commission o = new Commission(
                        j,
                        new Random().nextInt(2000),
                        new Deliveryman(
                                i*j*100,
                                "Fio_"+new Random().nextInt(200),
                                i,
                                j
                        ),
                        i);
                orders.add(o);
            }
            ds.setCommission(orders);

            List<Warehouse> warehouses = new ArrayList<>();
            for (int j = 1; j < 5; j++) {
                Warehouse w = new Warehouse(
                        j,
                        new Location(
                                "Country_"+new Random().nextInt(1000),
                                "City_"+new Random().nextInt(1000),
                                "Street_"+new Random().nextInt(1000),
                                "Houce_"+new Random().nextInt(1000),
                                i*j*100
                        ),
                        i);
                warehouses.add(w);
            }
            ds.setWarehouses(warehouses);
            deliveryServices.add(ds);

        }
        return deliveryServices;
    }

}
