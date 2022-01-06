package com.macro.mall.tiny.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author yangfei
 * @since 2019-05-18
 */
@EnableScheduling
@Component
@Slf4j
public class TaskConfig {
    @Scheduled(cron = "*/5 * * * * ?")
    public void testTaskInMemory() {
        log.info("running------");
    }
}
