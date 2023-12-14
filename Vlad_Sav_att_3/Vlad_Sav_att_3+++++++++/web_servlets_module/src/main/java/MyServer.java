import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class MyServer {
    private static final int PORT = 8080;
    private DB db;
    private QuaeryManeger quaeryManeger;

    public MyServer(DB db, QuaeryManeger quaeryManeger) {
        this.db = db;
        this.quaeryManeger = quaeryManeger;
    }

    public void drive() throws Exception {
        Server server = new Server(PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(new ServletHolder(new CommissionServlet(db, quaeryManeger)), "/Commission");
        context.addServlet(new ServletHolder(new DeliverymanServlet(db, quaeryManeger)), "/Deliveryman");
        context.addServlet(new ServletHolder(new LocationServlet(db, quaeryManeger)), "/Location");
        context.addServlet(new ServletHolder(new WarehouseServlet(db, quaeryManeger)), "/Warehouse");
        context.addServlet(new ServletHolder(new DeliveryServiceServlet(db, quaeryManeger)), "/DeliveryService");


        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { context });
        server.setHandler(handlers);
        server.start();
        server.join();
    }


}