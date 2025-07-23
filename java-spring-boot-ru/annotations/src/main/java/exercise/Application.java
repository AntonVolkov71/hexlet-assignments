package exercise;

import exercise.model.Address;
import exercise.annotation.Inspect;

import java.lang.reflect.Method;

public class Application {
    public static void main(String[] args) {
        var address = new Address("London", 12345678);

        // BEGIN
        for (Method method: Address.class.getDeclaredMethods()) {

            if (method.isAnnotationPresent(Inspect.class)) {
                try {
                    Object res = method.invoke(address);

                    Class<?> returnType = method.getReturnType();

                    String typeName = returnType.isPrimitive()
                            ? returnType.getSimpleName()
                            : res.getClass().getSimpleName();

                    System.out.println("Method " + method.getName() + " returns a value of type " + typeName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // END
    }
}
