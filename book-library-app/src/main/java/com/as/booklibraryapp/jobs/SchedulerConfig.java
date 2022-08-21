package com.as.booklibraryapp.jobs;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {

    @Bean
    public JobDetail userPrintJobDetail(){
        return JobBuilder
                .newJob(UserPrintJob.class)
                .withIdentity("userPrintJob")
                .storeDurably()
                .build();
    }

    @Bean
    public SimpleTrigger userPrintTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever();

        return TriggerBuilder
                .newTrigger()
                .forJob(userPrintJobDetail())
                .withIdentity("userPrintTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
