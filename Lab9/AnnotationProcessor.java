import java.lang.reflect.Method;

public class AnnotationProcessor {
    public static void main(String[] args) throws Exception {
        TestClass obj = new TestClass();
        Class<?> clazz = obj.getClass();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Invoke.class)) {
                Invoke annotation = method.getAnnotation(Invoke.class);
                method.setAccessible(true);

                for (int i = 0; i < annotation.count(); i++) {
                    if (method.getParameterCount() == 1) {
                        if (method.getParameterTypes()[0] == String.class) {
                            method.invoke(obj, "Hello " + i);
                        } else {
                            method.invoke(obj, i);
                        }
                    } else {
                        method.invoke(obj);
                    }
                }
            }
        }
    }
}