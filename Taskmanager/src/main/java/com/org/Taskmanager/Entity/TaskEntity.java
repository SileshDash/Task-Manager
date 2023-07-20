package com.org.Taskmanager.Entity;

import lombok.Data;

import java.util.Date;
@Data
public class TaskEntity {
    private int id;
    private String title;
    private String description;
    private Date  deadline;
    private Boolean Completed;
}
