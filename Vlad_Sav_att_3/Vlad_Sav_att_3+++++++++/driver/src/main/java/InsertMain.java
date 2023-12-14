import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class InsertMain {
    private static List<Class> classes = List.of(Deliveryman.class, DeliveryService.class, Location.class, Commission.class, Warehouse.class);
    private static DB db = new DB();



    private static QuaeryManeger qm = new QuaeryManeger(classes);

    private static void creatre() throws IOException, SQLException {
        for (Class c:classes) {
            db.execute(qm.create(c));
        }
    }
    private static void drop() throws IOException, SQLException {
        for (Class c:classes) {
            db.execute(qm.drop(c));
        }
    }

    public static void main(String[] args) throws Exception {
        drop();
        creatre();


        String fileName = "delivery.json";
        List<DeliveryService> deliveryServices = null;
        try {
            deliveryServices = new ObjectMapper().readValue(new FileInputStream(new File(fileName)),new TypeReference<List<DeliveryService>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (DeliveryService ds: deliveryServices) {
            db.execute(
                    qm.insert(ds)
            );

            for (Commission c:ds.getCommission()) {
                db.execute(
                        qm.insert(c)
                );
                db.execute(
                        qm.insert(c.getDeliveryman())
                );
            }
            for (Warehouse w:ds.getWarehouses()) {
                db.execute(
                        qm.insert(w)
                );
                db.execute(
                        qm.insert(w.getAddress())
                );
            }

        }



    }
}
