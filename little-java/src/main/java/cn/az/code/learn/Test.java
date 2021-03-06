package cn.az.code.learn;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.ThreadFactoryBuilder;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * @author Liz
 */
public class Test {

    public static void main(String[] args) {
        Map<String, String> map = MapUtil.newConcurrentHashMap();
        map.put("1", "222");
        ThreadFactory threadFactory = ThreadFactoryBuilder.create().setNamePrefix("test-thread-%d").build();
        ExecutorService service = ExecutorBuilder.create().setThreadFactory(threadFactory).setCorePoolSize(10).build();
        Object[] objects = Arrays.asList(1, 2, 3, 4, 5, 6).toArray();

        service.execute(() -> {
            objects.getClass();
        });
    }

    public static int hash(Object key) {
        int h;
        if (key == null) {
            return 0;
        } else {
            h = key.hashCode();
            // 2210095
            int s = h >>> 16;
            return h ^ s;
        }
    }
}
