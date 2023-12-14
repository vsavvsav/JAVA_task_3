import org.example.model.*;

import java.util.List;

public class WebMain {
    public static void main(String[] args) throws Exception {
        List<Class> classes = List.of(Deliveryman.class, DeliveryService.class, Location.class, Commission.class, Warehouse.class);
        DB db = new DB();
        QuaeryManeger qm = new QuaeryManeger(classes);

        MyServer myServer = new MyServer(db, qm);
        myServer.drive();
    }
}
