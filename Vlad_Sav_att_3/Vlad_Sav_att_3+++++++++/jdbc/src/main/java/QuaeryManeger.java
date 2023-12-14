import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuaeryManeger {
    private Map<Class, List<Field>> fields;
    private ReflectionHelper rh;



    public QuaeryManeger(List<Class> classes) {
        rh = new ReflectionHelper();
        initFields(classes);

    }

    private void initFields(List<Class> classes){
        fields = new HashMap<>();
        for (Class c:classes) {
            fields.put(c, rh.getSimpleFields(c));
        }
    }

    public String create(Class clz) throws IOException {
        System.out.println(read("jdbc\\src\\main\\resources\\create\\"+clz.getSimpleName()+".txt"));
        return read("jdbc\\src\\main\\resources\\create\\"+clz.getSimpleName()+".txt");
    }
    public String drop(Class clz) throws IOException {
        return read("jdbc\\src\\main\\resources\\drop\\"+clz.getSimpleName()+".txt");
    }

    public String findAll(Class clz){
        return "SELECT * FROM %s;".formatted(clz.getSimpleName());
    }
    public String find(Object obj) throws Exception {
        String quaery = "SELECT * FROM %s WHERE ".formatted(obj.getClass().getSimpleName());
        quaery+=getCondition(obj, " and ", " ;");
        if (quaery.charAt(quaery.length()-1) != ';')
            return findAll(obj.getClass());
        return quaery;
    }
    public String insert(Object obj) throws Exception {
        String quaery = "INSERT INTO %s VALUES (".formatted(obj.getClass().getSimpleName());
        Map<String, Object> values = getValues(obj);
        List<Field> names = fields.get(obj.getClass());
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i).getName();
            quaery+= getPerformance(values.get(name));
            quaery+= i != values.size()-1 ? " , " : " );";
        }
        return quaery;
    }
    public String delete(Object obj) throws Exception {
        String quaery = String.format("DELETE FROM %s WHERE ", obj.getClass().getSimpleName());
        quaery+=getCondition(obj, " and ", " ;");
        return quaery;
    }
    public String update(Object o1, Object o2) throws Exception {
        String quaery = "UPDATE %s SET ".formatted(o1.getClass().getSimpleName());
        quaery+= getCondition(o2, " , " , " WHERE ");
        quaery+= getCondition(o1, " and ", " ;");
        return quaery;
    }






    private String getPerformance(Object o) {
        return o.getClass().equals(String.class) ? String.format("\'%s\'", o.toString()) : o.toString();
    }




    private Map<String, Object> getValues(Object obj) throws Exception {
        Map<String, Object> values = new HashMap<>();
        for (Field f:fields.get(obj.getClass())) {
            Object val = rh.getValueByField(obj, f.getName());
            if (val != null)
                values.put(f.getName(), val);
        }
        return values;
    }
    private String getCondition(Object obj, String separatingSign, String lastSign) throws Exception {
        Map<String, Object> values = getValues(obj);
        List<String> names = new ArrayList<>(values.keySet());
        String quaery = "";
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            quaery+= " %s = %s ".formatted(name, getPerformance(values.get(name)));
            quaery+= i != names.size()-1 ? separatingSign : lastSign;
        }
        return quaery;
    }

    private String read(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        String st;
        String text = "";
        while ((st = br.readLine()) != null)
            text+=st;

        return text;
    }

    public List<Field> getField(Class clz){
        return fields.get(clz);
    }
    public ReflectionHelper getRh() {
        return rh;
    }
}
