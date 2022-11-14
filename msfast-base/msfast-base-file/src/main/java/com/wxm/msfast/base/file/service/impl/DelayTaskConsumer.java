package com.wxm.msfast.base.file.service.impl;

import com.wxm.msfast.base.file.common.quenue.DelayTaskQueue;
import com.wxm.msfast.base.file.rest.types.DelayTask;
import com.wxm.msfast.base.file.service.MsfFileService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;

@Data
@Slf4j
@Component
public class DelayTaskConsumer implements CommandLineRunner {

    @Autowired
    MsfFileService fileService;

    @Override
    public void run(String... args) throws Exception {
        DelayQueue<DelayTask> delayQueue = DelayTaskQueue.getInstance();//获取同一个put进去任务的队列
        /**启动一个线程，去取延迟消息**/
        Executors.newSingleThreadExecutor().execute(() -> {
            while (true) {
                // 从延迟队列的头部获取已经过期的消息
                // 如果暂时没有过期消息或者队列为空，则take()方法会被阻塞，直到有过期的消息为止
                DelayTask delayTask = null;//阻塞
                try {
                    delayTask = delayQueue.take();
                } catch (InterruptedException e) {
                }
                //删除临时文件
                if (delayTask != null && StringUtils.isNotBlank(delayTask.getUrl())) {
                    fileService.deleteTempFile(delayTask.getFilePath(), delayTask.getUrl());
                }
            }
        });
    }
}


