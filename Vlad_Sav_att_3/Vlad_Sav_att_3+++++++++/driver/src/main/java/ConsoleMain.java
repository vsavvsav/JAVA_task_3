import org.example.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ConsoleMain {

    private static List<Class> classes = List.of(Deliveryman.class, DeliveryService.class, Location.class, Commission.class, Warehouse.class);
    private static DB db = new DB();
    private static ConsoleProcessor cp = new ConsoleProcessor(classes);



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
        System.out.println(Location.class);

        //drop();
        creatre();


        cp.read();

    }
}
