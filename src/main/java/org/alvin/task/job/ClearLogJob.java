package org.alvin.task.job;

import org.alvin.task.common.anotations.TaskJobComponent;
import org.alvin.task.common.bean.IJobExecutor;
import org.alvin.task.common.bean.TaskJobTicket;
import org.springframework.stereotype.Component;

@Component
@TaskJobComponent("month")
public class ClearLogJob implements IJobExecutor {
    @Override
    public void execute(TaskJobTicket taskJobTicket) {
        System.out.println("一个月清理一次日志");
    }
}
