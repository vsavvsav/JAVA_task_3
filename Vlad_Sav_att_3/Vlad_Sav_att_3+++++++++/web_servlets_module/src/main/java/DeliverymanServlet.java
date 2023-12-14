import lombok.SneakyThrows;
import org.example.model.Deliveryman;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;

@WebServlet("/DeliverymanServlet")
public class DeliverymanServlet extends HttpServlet {
    private DB db;
    private QuaeryManeger quaeryManeger;

    public DeliverymanServlet(DB db, QuaeryManeger quaeryManeger) {
        this.db = db;
        this.quaeryManeger = quaeryManeger;
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Object object = Converter.setValues(quaeryManeger, request, new Deliveryman(), "");
        ResultSet resultSet = db.executeQuaery(quaeryManeger.find(object));
        response.getWriter().println(Converter.resultSetToString(quaeryManeger, resultSet, Deliveryman.class));
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object object = Converter.setValues(quaeryManeger, request, new Deliveryman(), "");
        db.execute(quaeryManeger.insert(object));
    }
    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object object = Converter.setValues(quaeryManeger, request, new Deliveryman(), "");
        db.execute(quaeryManeger.delete(object));
    }
    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object object1 = Converter.setValues(quaeryManeger, request, new Deliveryman(), "");
        Object object2 = Converter.setValues(quaeryManeger, request, new Deliveryman(), "SET");
        db.execute(quaeryManeger.update(object2, object1));
    }
}
