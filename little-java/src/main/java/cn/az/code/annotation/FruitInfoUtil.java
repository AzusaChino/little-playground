package cn.az.code.annotation;

import java.lang.reflect.Field;

/**
 * @author az
 */
public class FruitInfoUtil {

    protected FruitInfoUtil() {
    }

    public static void getFruitInfo(Class<?> clazz) {

        // 获取所以声明的属性 properties
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(FruitProvider.class)) {
                StringBuilder strFruitProvider = new StringBuilder("provider: ");
                FruitProvider fruitProvider = field.getAnnotation(FruitProvider.class);
                strFruitProvider.append(fruitProvider.id()).append(",").append(fruitProvider.name()).append(",").append(fruitProvider.address());
                System.out.println("strFruitProvider = " + strFruitProvider);
            }
        }
    }
}
