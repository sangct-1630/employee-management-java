package com.example.employee.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 30000)
    public void reportCurrentTime() {
        logger.info("System running... Thời gian hiện tại: {}", dateFormat.format(new Date()));
    }

    @Scheduled(fixedRate = 60000)
    @CacheEvict(value = "employeeCount", allEntries = true)
    public void clearEmployeeCountCache() {
        logger.info("🧹 Đã xóa Cache 'employeeCount' (Làm mới dữ liệu)");
    }
}
