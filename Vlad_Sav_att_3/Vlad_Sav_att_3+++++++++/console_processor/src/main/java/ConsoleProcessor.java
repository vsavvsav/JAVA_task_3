import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ConsoleProcessor {
    private DB db;

    private QuaeryManeger qm;

    private List<Class> classes;

    public ConsoleProcessor(List<Class> classes) {
        this.classes = classes;
        db = new DB();
        qm = new QuaeryManeger(classes);
    }
    public void read() throws Exception {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("command: ");
            String command = scanner.nextLine().trim();
            System.out.println();
            if (command.equals("help")) {
                System.out.println("findall");
                System.out.println("find");
                System.out.println("insert");
                System.out.println("delete");
                System.out.println("update");
                System.out.println("update");
                System.out.println();
                System.out.println("close");
            }
            else if (command.equals("close")) {
                return;
            }else{
                System.out.print("table name: ");
                String tableName = scanner.nextLine().trim();
                process(command, tableName);
                System.out.println();
            }
        }

    }
    public void process(String command, String tableName) throws Exception {
        if(!isValidClass(tableName)) {
            System.out.println("table is not found");
            return;
        }

        Class c = Class.forName(String.format("org.example.model.%s", tableName));
        Object o1 = c.newInstance();



        if(command.equals("findall")){
            printResultSet(
                    db.executeQuaery(
                            qm.findAll(o1.getClass())
                    ),
                    o1
            );
        } else if (command.equals("find")) {
            setValues(o1);
            printResultSet(db.executeQuaery(qm.find(o1)), o1);
        } else if (command.equals("delete")) {
            setValues(o1);
            db.execute(qm.delete(o1));
        } else if (command.equals("insert")) {
            setValues(o1);
            db.execute(qm.insert(o1));
        } else if (command.equals("update")) {
            System.out.println("from: ");
            setValues(o1);
            System.out.println("to: ");
            Class c2 = Class.forName(String.format("org.example.model.%s", tableName));
            Object o2 = c.newInstance();
            setValues(o2);
            db.execute(qm.update(o2, o1));
        } else {
            System.out.println("command not found");
        }

    }


    private void printResultSet(ResultSet rs, Object o1) throws SQLException {
        List<Field> fields = qm.getField(o1.getClass());
        while (rs.next()){
            for (Field f:fields) {
                System.out.println(
                        "%s: %s".formatted(f.getName(), rs.getObject(f.getName()))
                );
            }
            System.out.println();
        }
    }
    private Object setValues(Object o) throws NoSuchFieldException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        for (Field f:qm.getField(o.getClass())) {
            System.out.print(f.getName()+": ");
            String value = scanner.nextLine().trim();
            if(value.length() == 0)
                continue;
            System.out.println();
            qm.getRh().setValue(o, f.getName(), castValue(value, f.getType()));
        }
        return o;
    }

    private Object castValue(String val, Class type){
        if (type.equals(Integer.class) || type.equals(int.class))
            return Integer.parseInt(val);
        if (type.equals(Long.class) || type.equals(long.class))
            return Long.parseLong(val);
        return val;
    }

    private boolean isValidClass(String cls){
        for (Class c:classes) {
            if(c.getSimpleName().equals(cls))
                return true;
        }
        return false;
    }


}
