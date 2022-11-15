package com.wxm.msfast.base.file.utils;

import com.wxm.msfast.base.file.common.quenue.DelayTaskQueue;
import com.wxm.msfast.base.file.rest.types.DelayTask;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.DelayQueue;

/**
 * 在需要使用到延时队列的业务进行投递任务(消息)
 */
@Slf4j
public class DelayTaskProducer {

    public static void put(String filePath, String url, Long time) {
        DelayQueue<DelayTask> delayQueue = DelayTaskQueue.getInstance();//创建队列 1
        DelayTask delayTask = new DelayTask();//创建任务
        delayTask.setFilePath(filePath).setUrl(url).setTime(time);
        log.info("=============入延时队列,{}", delayTask);
        boolean offer = delayQueue.offer(delayTask);//任务入队
        if (offer) {
            log.info("=============入延时队列成功,{}", delayTask);
        } else {
            log.info("=============入延时队列失败");
        }
    }

    public static void remove(String url) {
        DelayQueue<DelayTask> delayQueue = DelayTaskQueue.getInstance();
        delayQueue.removeIf(p -> p.getUrl().equals(url));
        log.info("=============移除延时队列,{}", url);
    }
}

