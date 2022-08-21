package com.as.booklibraryapp.jobs;

import com.as.booklibraryapp.domain.Users;
import com.as.booklibraryapp.repository.JpaUserRepository;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class UserPrintJob extends QuartzJobBean {

    private Logger log = LoggerFactory.getLogger(UserPrintJob.class);

    private final JpaUserRepository jpaUserRepository;

    public UserPrintJob(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Iterable<Users> users = jpaUserRepository.findAll();

        if (users.iterator().hasNext()){
            log.info("There are users currently entered in app");

            users.forEach(
                    it -> log.info(it.getName())
            );
        }
        else {
            log.info("There are no users currently entered in app");
        }
    }
}
