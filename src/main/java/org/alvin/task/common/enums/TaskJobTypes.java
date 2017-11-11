package org.alvin.task.common.enums;

/**
 * 常用任务类型,如果多个任务具有相同的属性，就定义一个
 */
public enum TaskJobTypes {

    DEFAULT_TASK , //默认类型，只是为了标注
    MONTH_TASK , //每月执行一次
    HOUR_TASK, //常用每小时执行一次的任务
    DAY_TASK ,//常用每天凌晨0点开始执行的任务
}
