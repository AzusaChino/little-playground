package cn.az.code.limit;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.IntStream;

/**
 * 滑动窗口 <b>滑动窗口是对计数器方式的改进,增加一个时间粒度的度量单位</b>
 *
 * @author <a href="mailto:azusa146@gmail.com">az</a>
 * @see ConcurrentLinkedQueue
 * @since 2020-03-12
 */
public class TimeWindow {

    private final ConcurrentLinkedQueue<Long> queue = new ConcurrentLinkedQueue<>();

    /**
     * 间隔秒数
     */
    private final int seconds;

    /**
     * 最大限流
     */
    private final int max;

    public TimeWindow(int max, int seconds) {
        this.seconds = seconds;
        this.max = max;

        /**
         * 永续线程执行清理queue 任务
         */
        new Thread(() -> {
            while (true) {
                try {
                    // 等待 间隔秒数-1 执行清理操作
                    Thread.sleep((seconds - 1) * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                clean();
            }
        }).start();

    }

    public static void main(String[] args) {

        final TimeWindow timeWindow = new TimeWindow(10, 1);

        // 测试3个线程
        IntStream.range(0, 3).forEach((i) -> {
            new Thread(() -> {

                while (true) {

                    try {
                        Thread.sleep(new Random().nextInt(20) * 100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    timeWindow.take();
                }

            }).start();

        });

    }

    /**
     * 获取令牌，并且添加时间
     */
    public void take() {

        try {

            int size = sizeOfValid();
            if (size > max) {
                System.err.println("超限");

            }
            synchronized (queue) {
                if (sizeOfValid() > max) {
                    System.err.println("超限");
                    System.err.println("queue中有 " + queue.size() + " 最大数量 " + max);
                }
                this.queue.offer(System.currentTimeMillis());
            }
            System.out.println("queue中有 " + queue.size() + " 最大数量 " + max);

        } catch (Exception ignored) {

        }

    }

    public int sizeOfValid() {
        Iterator<Long> it = queue.iterator();
        long ms = System.currentTimeMillis() - seconds * 1000;
        int count = 0;
        while (it.hasNext()) {
            long t = it.next();
            if (t > ms) {
                // 在当前的统计时间范围内
                count++;
            }
        }

        return count;
    }

    /**
     * 清理过期的时间
     */
    public void clean() {
        long c = System.currentTimeMillis() - seconds * 1000;

        Long tl;
        while ((tl = queue.peek()) != null && tl < c) {
            System.out.println("清理数据");
            queue.poll();
        }
    }

}
