package org.alvin.task.common.bean;

import java.util.Date;

/**
 * 任务票据
 */
public class TaskJobTicket {

    //票据产生时间
    private Date date;
    //票据对应任务类型
    private String taskJobTypes;
    //票据优先级
    private int priority;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTaskJobTypes() {
        return taskJobTypes;
    }

    public void setTaskJobTypes(String taskJobTypes) {
        this.taskJobTypes = taskJobTypes;
    }


    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
