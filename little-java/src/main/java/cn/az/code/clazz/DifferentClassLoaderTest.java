package cn.az.code.clazz;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author az
 */
public class DifferentClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader classLoader = new ClassLoader() {
            /**
             * Loads the class with the specified <a href="#name">binary name</a>. This
             * method searches for classes in the same manner as the
             * {@link #loadClass(String, boolean)} method. It is invoked by the Java virtual
             * machine to resolve class references. Invoking this method is equivalent to
             * invoking {@link #loadClass(String, boolean) <tt>loadClass(name,
             * false)</tt>}.
             *
             * @param name The <a href="#name">binary name</a> of the class
             * @return The resulting <tt>Class</tt> object
             * @throws ClassNotFoundException If the class was not found
             */
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                InputStream inputStream = getClass().getResourceAsStream(fileName);
                if (Objects.isNull(inputStream)) {
                    return super.loadClass(name);
                }
                try {
                    byte[] b = new byte[inputStream.available()];
                    inputStream.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return super.loadClass(name);
            }
        };
        // throw ClassCastException (differentClassLoader) 两者不属于同一类加载器加载，所以不能转化！
        // Class<?> obj =
        // classLoader.loadClass("cn.az.code.clazz.DifferentClassLoaderTest");

        Class<?> o = classLoader.loadClass("cn.az.code.clazz.DifferentClassLoaderTest");
        System.out.println(o);
    }
}
