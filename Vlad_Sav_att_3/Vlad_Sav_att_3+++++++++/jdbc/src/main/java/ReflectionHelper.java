import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectionHelper {

    public ReflectionHelper() {
    }


    public Object getValueByField(Object object, String fieldName) throws Exception {
        Class<?> clazz = object.getClass();
        Method getter = clazz.getMethod("get" + capitalizeFirstLetter(fieldName));
        return getter.invoke(object);
    }

    private String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public void setValue(Object o, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = o.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(o, value);
    }

    public List<Field> getSimpleFields(Class clz){
        List<Field> fields = new ArrayList<>();
        Field[] fs = clz.getDeclaredFields();
        for(int i = 0; i < fs.length; i++) {
            if(isSimple(fs[i].getType()))
                fields.add(fs[i]);
        }
        return fields;
    }
    public boolean isSimple(Class clz){
        return clz.equals(Long.class) || clz.equals(Integer.class) || clz.equals(long.class) || clz.equals(int.class) || clz.equals(String.class);
    }




}
