package cn.az.code.lambda;

import cn.az.code.function.ThrowingConsumer;
import cn.hutool.log.Log;

import java.util.function.Consumer;

/**
 * @author Liz
 */
public class LambdaWrapper {

    private static final Log log = Log.get();

    public static Consumer<Integer> lambdaWrapper(Consumer<Integer> consumer) {
        return i -> {
            try {
                consumer.accept(i);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        };
    }

    public static <T, E extends Exception> Consumer<T> lambdaWrapper(Consumer<T> consumer, Class<E> clazz) {
        return i -> {
            try {
                consumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = clazz.cast(ex);
                    log.info(exCast.getMessage());
                } catch (ClassCastException e) {
                    log.error(e.getMessage());
                }
            }
        };
    }

    public static <T> Consumer<T> throwingConsumerWrapper(ThrowingConsumer<T, Exception> throwingConsumer) {
        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    public static <T, E extends Exception> Consumer<T> handlingConsumerWrapper(
            ThrowingConsumer<T, E> throwingConsumer, Class<E> exceptionClass) {

        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = exceptionClass.cast(ex);
                    log.error("Exception occurred : {}", exCast.getMessage());
                } catch (ClassCastException ccEx) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }
}
