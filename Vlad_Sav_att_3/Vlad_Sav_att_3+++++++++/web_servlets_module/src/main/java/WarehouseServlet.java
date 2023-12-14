import lombok.SneakyThrows;
import org.example.model.Warehouse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;

@WebServlet("/WarehouseServlet")

public class WarehouseServlet extends HttpServlet {
    private DB db;
    private QuaeryManeger quaeryManeger;
    public WarehouseServlet(DB db, QuaeryManeger quaeryManeger) {
        this.db = db;
        this.quaeryManeger = quaeryManeger;
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Object object = Converter.setValues(quaeryManeger, request, new Warehouse(), "");
        ResultSet resultSet = db.executeQuaery(quaeryManeger.find(object));
        response.getWriter().println(Converter.resultSetToString(quaeryManeger, resultSet, Warehouse.class));
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object object = Converter.setValues(quaeryManeger, request, new Warehouse(), "");
        db.execute(quaeryManeger.insert(object));
    }
    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object object = Converter.setValues(quaeryManeger, request, new Warehouse(), "");
        db.execute(quaeryManeger.delete(object));
    }
    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object object1 = Converter.setValues(quaeryManeger, request, new Warehouse(), "");
        Object object2 = Converter.setValues(quaeryManeger, request, new Warehouse(), "SET");
        db.execute(quaeryManeger.update(object2, object1));
    }
}
