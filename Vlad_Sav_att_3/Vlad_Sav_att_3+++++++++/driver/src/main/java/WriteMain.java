import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.Generator;
import org.example.model.DeliveryService;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class WriteMain {
    public static void main(String[] args) {
        String fileName = "delivery.json";
        Generator g = new Generator(10);

        List<DeliveryService> deliveryServices = g.generate();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);


        try {
            objectMapper.writeValue(new File(fileName), deliveryServices);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}