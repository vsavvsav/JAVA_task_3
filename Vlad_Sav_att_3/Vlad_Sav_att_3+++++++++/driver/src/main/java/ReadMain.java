import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Commission;
import org.example.model.DeliveryService;
import org.example.model.Warehouse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;



public class ReadMain {
    public static void main(String[] args) {
        String fileName = "delivery.json";
        List<DeliveryService> deliveryServices = null;
        try {
            deliveryServices = new ObjectMapper().readValue(new FileInputStream(new File(fileName)),new TypeReference<List<DeliveryService>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }



        for (DeliveryService ds:deliveryServices) {
            System.out.println(ds.getId());
            System.out.println(ds.getName());
            for (Commission o : ds.getCommission()) {
                System.out.println(o);
            }
            System.out.println();
            for (Warehouse ws: ds.getWarehouses()) {
                System.out.println(ws);
            }
            System.out.println();
            System.out.println();
            System.out.println();
        }

    }

}