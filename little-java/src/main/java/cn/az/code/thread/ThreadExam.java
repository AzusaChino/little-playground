package cn.az.code.thread;

import cn.hutool.log.Log;

import java.util.concurrent.CountDownLatch;

/**
 * 每完成一个线程，计数器减1，当减到0时，被阻塞的线程自动执行。
 *
 * @author az
 * @date 2020/3/22
 */
public class ThreadExam {

    private static Log log = Log.get();

    private static final int COUNT = 20;

    static CountDownLatch cdl = new CountDownLatch(COUNT);

    public static void main(String[] args) throws InterruptedException {
        new Thread(new TeacherRunnable(cdl)).start();
        Thread.sleep(1000L);
        for (int i = 0; i < COUNT; i++) {
            new Thread(new StudentRunnable(i, cdl)).start();
        }

        synchronized (ThreadExam.class) {
            ThreadExam.class.wait();
        }
    }

    static class TeacherRunnable implements Runnable {

        CountDownLatch cdl;

        TeacherRunnable(CountDownLatch cdl) {
            this.cdl = cdl;
        }

        @Override
        public void run() {
            log.info("sending out papers...");
            try {
                this.cdl.wait();
            } catch (InterruptedException e) {
                log.error(e.getLocalizedMessage(), e);
            }
            log.info("picking up papers...");
        }
    }

    static class StudentRunnable implements Runnable {

        CountDownLatch cdl;
        Integer num;

        StudentRunnable(int num, CountDownLatch cdl) {
            this.num = num;
            this.cdl = cdl;
        }

        @Override
        public void run() {
            log.info("{} is writing hard...", this.num);
            try {
                Thread.sleep(5 * 1000L);
            } catch (InterruptedException e) {
                log.warn(e);
            }
            log.info("{} is finished the paper", this.num);
            cdl.countDown();
        }
    }
}
