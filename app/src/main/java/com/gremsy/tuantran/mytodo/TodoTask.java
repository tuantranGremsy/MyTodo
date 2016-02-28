package com.gremsy.tuantran.mytodo;

import java.io.Serializable;

/**
 * Created by Administrator on 2/26/2016.
 */
public class TodoTask implements Serializable {
    String task;
    String priority;
    public TodoTask(String task, String priority) {
        this.task = task;
        this.priority = priority;
    }
}
