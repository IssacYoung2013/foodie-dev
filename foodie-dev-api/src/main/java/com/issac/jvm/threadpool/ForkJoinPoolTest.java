package com.issac.jvm.threadpool;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author: ywy
 * @date: 2021-02-10
 * @desc:
 */
public class ForkJoinPoolTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Integer> task = pool.submit(new MyTask(1, 100));
        System.out.println(task.get());
    }
}

class MyTask extends RecursiveTask<Integer> {
    /**
     * 当前任务计算的起始
     */
    private int start;
    private int end;
    /**
     * 阈值，如果 end-start在阈值以内，就不用细分任务
     */
    private static final int threshold = 2;

    public MyTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean needFork = (end - start) > threshold;
        if (needFork) {
            int mid = (start + end) / 2;
            MyTask leftTask = new MyTask(start, mid);
            MyTask rightTask = new MyTask(mid + 1, end);

            // 执行子任务
            leftTask.fork();
            rightTask.fork();

            Integer leftResult = leftTask.join();
            Integer rightResult = rightTask.join();
            sum = leftResult + rightResult;
        } else {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        }
        return sum;
    }
}
