package org.alvin.task.job;

import org.alvin.task.common.bean.TaskJobTicket;
import org.springframework.stereotype.Component;

import org.alvin.task.common.anotations.TaskJobComponent;
import org.alvin.task.common.bean.IJobExecutor;

/**
 * 停卡业务任务执行
 */
@Component
@TaskJobComponent("day")
public class DayJobDemo1 implements IJobExecutor {

    @Override
    public void execute(TaskJobTicket taskJobTicket) {
        System.out.println("虽然我是天，但是我计时不按天的跑，嘿嘿");
    }
}
