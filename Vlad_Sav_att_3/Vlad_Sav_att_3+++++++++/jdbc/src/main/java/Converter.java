import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Converter {

    public static String resultSetToString(QuaeryManeger qm, ResultSet rs, Object o1) throws SQLException {
        List<Field> fields = qm.getField(o1.getClass());
        String s = "Objects: ";
        while (rs.next()){
            for (Field f:fields) {
                s+= "%s: %s\n".formatted(f.getName(), rs.getObject(f.getName()));

            }
            s+="\n\n";
        }
        return s;
    }
    public static Object setValues(QuaeryManeger qm, HttpServletRequest request, Object o, String prefix) throws NoSuchFieldException, IllegalAccessException {

        for (Field f:qm.getField(o.getClass())) {

            String value = request.getParameter(prefix+f.getName());
            if(value.length() == 0)
                continue;

            qm.getRh().setValue(o, f.getName(), castValue(value, f.getType()));
        }
        return o;
    }

    public static Object castValue(String val, Class type){
        if (type.equals(Integer.class) || type.equals(int.class))
            return Integer.parseInt(val);
        if (type.equals(Long.class) || type.equals(long.class))
            return Long.parseLong(val);
        return val;
    }


}
