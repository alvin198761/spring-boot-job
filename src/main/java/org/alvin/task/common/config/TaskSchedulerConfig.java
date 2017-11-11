package org.alvin.task.common.config;

import org.alvin.task.common.anotations.TaskJobComponent;
import org.alvin.task.common.bean.IJobExecutor;
import org.alvin.task.common.bean.TaskJobTicket;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("donglan.task")
public class TaskSchedulerConfig implements SchedulingConfigurer, InitializingBean {

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        this.jobCrons.forEach((k, v) -> scheduledTaskRegistrar.addCronTask(new CronTask(() -> createTask(k), v)));
    }

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private BlockingQueue<TaskJobTicket> taskQueue;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private Map<String, String> jobCrons;

    public void createTask(String key) {
        TaskJobTicket taskJobTicket = new TaskJobTicket();
        taskJobTicket.setDate(new Date());
        taskJobTicket.setTaskJobTypes(key);
        taskJobTicket.setPriority(1);
        try {
            this.taskQueue.put(taskJobTicket);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(this::jobStart).start();
    }

    public void jobStart() {
        //获取任务，并根据任务类型执行
        while (true) {
            try {
                TaskJobTicket taskJobTicket = this.taskQueue.take();
                //将获得的任务放入线程池
                getJobs(taskJobTicket.getTaskJobTypes()).forEach(item -> taskExecutor.submit(() -> item.execute(taskJobTicket)));
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 根据任务类型获得相同类型的任务
     *
     * @param taskJobTypes
     * @return
     */
    private List<IJobExecutor> getJobs(String taskJobTypes) {
        Map<String, Object> beanMaps = applicationContext.getBeansWithAnnotation(TaskJobComponent.class);
        return beanMaps.values().stream().filter(item -> {
            TaskJobComponent taskJobComponent = item.getClass().getAnnotation(TaskJobComponent.class);
            return taskJobComponent.value().equalsIgnoreCase(taskJobTypes);
        }).map(item -> (IJobExecutor) item
        ).collect(Collectors.toList());
    }

    public Map<String, String> getJobCrons() {
        return jobCrons;
    }

    public void setJobCrons(Map<String, String> jobCrons) {
        this.jobCrons = jobCrons;
    }
}
