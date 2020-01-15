package cn.az.code.stream;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author Liz
 * @date 2020/1/8
 */
@Slf4j
public class ParallelDemo {

    public static long parallelRun(int n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    public static void measurePerformance(Function<Long, Long> function, long n) {
        long longest = Long.MAX_VALUE;
        LongStream.rangeClosed(1, 10).forEach(l -> {
            long startTime = System.nanoTime();
            long sum = function.apply(n);
            long duration = (System.nanoTime() - startTime) / 1_000_000;
            log.info("Consuming: {}", duration);
        });
    }
}