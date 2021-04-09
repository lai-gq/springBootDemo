package com.lai.demo.tasks;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

/**
 * 功能描述：异步任务业务类
 * 三个任务：
 * 1.耗时1秒
 * 2.耗时2秒
 * 3.耗时3秒
 * 同步(一个一个来)开启任务耗时为1+2+3=6秒
 * 异步(一起执行)开启任务耗时 3秒
 */
@Component
@Async
public class AsyncTask {
    //获取异步结果
    public Future<String> task1() throws InterruptedException{
        long begin = System.currentTimeMillis();
        Thread.sleep(1000L);
        long end = System.currentTimeMillis();
        System.out.println("任务1耗时="+(end-begin));
        return new AsyncResult<String>("任务1");
    }
    
    public Future<String> task2() throws InterruptedException{
        long begin = System.currentTimeMillis();
        Thread.sleep(2000L);
        long end = System.currentTimeMillis();
        System.out.println("任务2耗时="+(end-begin));
        return new AsyncResult<String>("任务2");
    }
    
    public Future<String> task3() throws InterruptedException{
        long begin = System.currentTimeMillis();
        Thread.sleep(3000L);
        long end = System.currentTimeMillis();
        System.out.println("任务3耗时="+(end-begin));
        return new AsyncResult<String>("任务3");
    }
}