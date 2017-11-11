package org.alvin.task.job;

import org.alvin.task.common.anotations.TaskJobComponent;
import org.alvin.task.common.bean.IJobExecutor;
import org.alvin.task.common.bean.TaskJobTicket;
import org.springframework.stereotype.Component;

/**
 * 停卡业务任务执行
 */
@Component
@TaskJobComponent("day")
public class DayJobDemo2 implements IJobExecutor {

    @Override
    public void execute(TaskJobTicket taskJobTicket) {
        System.out.println("开始了，开始了");
    }
}
