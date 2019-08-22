package com.robust.concurrency.lock.semaphore;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Description:
 * @Author: robust
 * @CreateDate: 2019/4/9 16:20
 * @Version: 1.0
 */
@Slf4j
public class SemaphoreExample1 {
    private final static int threadCount = 20;

    public static void main(String[] args) throws Exception {

        ExecutorService exec = Executors.newCachedThreadPool();
        // 每次最多三个线程获取许可
        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    semaphore.acquire(); // 获取一个许可
                    test(threadNum);
                    semaphore.release(); // 释放一个许可
                } catch (Exception e) {
//                    log.error("exception", e);

                }
            });
        }
        exec.shutdown();
    }

    private static void test(int threadNum) throws Exception {
//        log.info("{}", threadNum);
        Thread.sleep(1000);
    }
}
