package model;

import enums.Status;

import java.io.Serial;
import java.io.Serializable;

public class Task implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    private String taskName;
    private String taskDescription;
    private int taskId;
    private Status taskStatus;


    public Task(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = Status.NEW;
    }

    public Task() {
        super();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "<html>TaskId: " + taskId +
                "&emsp &ensp Name: " + taskName +
                "&emsp &ensp Description: " + taskDescription +
                "&emsp &ensp Status: " + taskStatus +
                "<br></html>";
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public int getTaskId() {
        return taskId;
    }

    public Status getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Status taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void changeTaskStatus() {
        if (getTaskStatus().equals(Status.NEW)) {
            setTaskStatus(Status.IN_PROGRESS);
        } else if (getTaskStatus().equals(Status.IN_PROGRESS)) {
            setTaskStatus(Status.DONE);
        }
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}